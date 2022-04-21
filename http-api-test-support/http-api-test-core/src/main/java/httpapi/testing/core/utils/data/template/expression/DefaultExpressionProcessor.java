package httpapi.testing.core.utils.data.template.expression;

import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import httpapi.testing.core.utils.data.JsonParser;
import httpapi.testing.core.utils.data.template.expression.model.Action;
import httpapi.testing.core.utils.data.template.expression.model.Expression;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

@Slf4j
@RequiredArgsConstructor
public class DefaultExpressionProcessor implements RequestBodyModifierExpressionProcessor {
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile(
        "^(?<action>" + Action.SET.getKeyword() + "|" + Action.REMOVE.getKeyword() + ") (?<jsonPath>\\S+)($| to "
            + "((?<simpleValue>(?<type>%s|%d|%f) (?<value>.+)$)"
            + "|(?<pointer>(?<referencePath>.+) using (?<fragmentJsonRelativeFilePath>.+)$)))"
    );

    private final JsonParser jsonParser;
    private final Configuration jsonPathConfig;

    public Expression process(String expression) {
        Matcher matcher = EXPRESSION_PATTERN.matcher(expression);
        if (!matcher.matches()) {
            throw new IllegalStateException(
                "No pattern matched for: " + expression + "\n Supported random value placeholders: " + EXPRESSION_PATTERN);
        }

        return Expression.builder()
            .action(Action.getByKeyword(matcher.group("action").trim()))
            .jsonPathToSet(matcher.group("jsonPath").trim())
            .value(parseValue(matcher))
            .build();
    }

    private Object parseValue(Matcher matcher) {
        Object ret = null;
        if (matcher.group("pointer") != null) {
            ret = resolveFragment(matcher);
        } else if (matcher.group("simpleValue") != null) {
            var value = RandomValuePlaceholderResolver.resolve(matcher);
            ret = ValueTypeConverter.convert(matcher.group("type").trim(), value);
        }
        return ret;
    }

    private Object resolveFragment(Matcher matcher) {
        var fragmentRelativePath = matcher.group("fragmentJsonRelativeFilePath").trim();
        var fragmentReference = jsonParser.parseRequestPayloadFragmentJsonFromFile(fragmentRelativePath);
        var value = matcher.group("referencePath").trim();
        return JsonPath.using(jsonPathConfig).parse(fragmentReference.toPrettyString()).read(value);
    }

    @Value
    private static final class ValueTypeConverter {
        private static final Map<String, Function<String, Object>> TYPE = Map.of(
            "%d", Integer::valueOf,
            "%f", Float::valueOf,
            "%s", String::valueOf
        );

        public static Object convert(String type, String value) {
            return TYPE.get(type).apply(value);
        }
    }

    @Value
    private static final class RandomValuePlaceholderResolver {
        private static final java.util.regex.Pattern RANDOM_PLACEHOLDER_PATTERN = Pattern.compile(
            "^#\\{(?<type>randomAlphabetic|randomAlphaNumeric|randomNumeric)\\((?<length>[0-9]*)\\)}$"
        );

        public static String resolve(Matcher matcher) {
            String ret = matcher.group("value").trim();
            return ret.startsWith("#{") ? resolveRandomValuePlaceholder(ret) : ret;
        }

        private static String resolveRandomValuePlaceholder(String input) {
            String ret = input;
            Matcher m = RANDOM_PLACEHOLDER_PATTERN.matcher(ret);
            if (m.matches()) {
                var length = Integer.parseInt(m.group("length"));
                switch (m.group("type")) {
                    case "randomAlphabetic":
                        ret = RandomStringUtils.randomAlphabetic(length);
                        break;
                    case "randomAlphaNumeric":
                        ret = RandomStringUtils.randomAlphanumeric(length);
                        break;
                    case "randomNumeric":
                        ret = RandomStringUtils.randomNumeric(length);
                        break;
                    default:
                        break;
                }
            } else {
                throw new IllegalStateException(
                    "No pattern matched for: " + ret + "\n Supported random value placeholders: " + RANDOM_PLACEHOLDER_PATTERN);
            }
            return ret;
        }
    }
}
