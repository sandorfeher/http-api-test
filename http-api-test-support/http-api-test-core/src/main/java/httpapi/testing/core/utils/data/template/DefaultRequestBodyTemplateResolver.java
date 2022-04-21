package httpapi.testing.core.utils.data.template;

import java.util.Map;
import java.util.function.BiConsumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import httpapi.testing.core.utils.data.template.expression.model.Action;
import httpapi.testing.core.utils.data.template.expression.model.Expression;
import httpapi.testing.core.utils.data.template.model.RequestBodyTemplate;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public class DefaultRequestBodyTemplateResolver implements RequestBodyTemplateResolver {
    private final Configuration config;

    public JsonNode resolve(RequestBodyTemplate requestBodyTemplate) {
        var requestBody = JsonPath.using(config).parse(requestBodyTemplate.getResourceTemplate());
        requestBodyTemplate.getResolutionValues().forEach(resolutionValue -> Resolver.resolve(requestBody, resolutionValue));
        return requestBody.json();
    }

    @Value
    private static final class Resolver {
        private static final Map<Action, BiConsumer<DocumentContext, Expression>> ACTION_BI_CONSUMER_MAP = Map.of(
            Action.SET, (doc, expression) -> doc.set(expression.getJsonPathToSet(), expression.getValue()),
            Action.REMOVE, (doc, expression) -> doc.delete(expression.getJsonPathToSet())
        );

        public static void resolve(DocumentContext doc, Expression expression) {
            ACTION_BI_CONSUMER_MAP.get(expression.getAction()).accept(doc, expression);
        }
    }
}
