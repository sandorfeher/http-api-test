package httpapi.testing.core.steps;

import java.util.List;

import httpapi.testing.core.utils.data.JsonParser;
import httpapi.testing.core.utils.data.template.RequestBodyTemplateResolver;
import httpapi.testing.core.utils.data.template.expression.model.Expression;
import httpapi.testing.core.utils.data.template.model.RequestBodyTemplate;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class HttpRequestBodySteps {
    private final JsonParser jsonParser;
    private final RequestBodyTemplateResolver templateResolver;
    private final HttpRequestSteps apiSteps;

    @Given("I have a http request body from {word}")
    public void getHttpRequestBodyTemplateFrom(String relativeFilePath) {
        var requestBody = jsonParser.parseHttpRequestBodyJsonFromFile(relativeFilePath);
        apiSteps.setRequestBody(requestBody);
    }

    @Given("I have a http request body template from {word} with")
    public void getHttpRequestBodyTemplateFrom(String relativePath, List<Expression> resolutionValues) {
        var requestBodyTemplate = RequestBodyTemplate.builder()
            .resourceTemplate(jsonParser.parseHttpRequestBodyJsonFromFile(relativePath))
            .resolutionValues(resolutionValues)
            .build();
        var requestBody = templateResolver.resolve(requestBodyTemplate);
        apiSteps.setRequestBody(requestBody);
    }

}
