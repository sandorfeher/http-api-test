package httpapi.testing.core.utils.data.requestbody;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultRequestBodyModifier implements RequestBodyModifier {
    private final Configuration jsonPathConfig;

    @Override
    public JsonNode modifyRequestBodyField(JsonNode requestBody, String jsonPath, Object value) {
        return JsonPath.using(jsonPathConfig).parse(requestBody).set(jsonPath, value).json();
    }

    @Override
    public JsonNode removeRequestBodyField(JsonNode requestBody, String jsonPath) {
        return JsonPath.using(jsonPathConfig).parse(requestBody).delete(jsonPath).json();
    }
}
