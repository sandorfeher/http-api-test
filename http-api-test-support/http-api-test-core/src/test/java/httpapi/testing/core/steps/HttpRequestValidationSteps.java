package httpapi.testing.core.steps;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class HttpRequestValidationSteps {
    private final Configuration JsonPathConfig;
    private final HttpRequestSteps apiSteps;

    @Then("http request body should contain")
    public void requestBodyShouldContain(DataTable table) {
        var requestBody = getRequestBody();
        table.entries().forEach(e -> {
            var field = requestBody.read(e.get("json_path"), String.class);
            var expected = e.get("value");

            assertThat(field)
                .describedAs("value of field '%s' is incorrect.", e.get("json-path"))
                .isEqualTo(expected);
        });
    }

    @Then("http request body field length is:")
    public void theValueLength(DataTable table) {
        var requestBody = getRequestBody();
        table.entries().forEach(e -> {
            var field = requestBody.read(e.get("json_path"), String.class).length();
            var expected = Integer.parseInt(e.get("value"));

            assertThat(field)
                .describedAs("value of field '%s' is incorrect.", e.get("json-path"))
                .isEqualTo(expected);
        });
    }

    @Then("http request body should NOT contain fields:")
    public void theRequestShouldNOTContain(List<String> jsonPaths) {
        var requestBody = getRequestBody();
        jsonPaths.forEach(jsonPath ->
        {
            var field = requestBody.read(jsonPath, String.class);
            assertThat(field)
                .as("Request should NOT contain field '%s'.", jsonPath)
                .isNull();
        });
    }

    private DocumentContext getRequestBody() {
        return JsonPath.using(JsonPathConfig).parse(apiSteps.getRequestBody());
    }
}
