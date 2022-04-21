package httpapi.testing.core.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.restassured.http.Header;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class HttpResponseVerificationSteps {
    private final Configuration jsonPathConfig;
    private final HttpRequestSteps apiSteps;

    @Then("status code should be {int}")
    public void thenStatusCodeShouldBe(int statusCode) {
        apiSteps.getResponse()
            .then()
            .assertThat()
            .statusCode(statusCode);
    }

    @Then("the response header should contain")
    public void thenResponseHeaderShouldContain(DataTable table) {
        for (Map<String, String> columns : table.entries()) {
            Header actualHeader = apiSteps.getResponse().headers().get(columns.get("header_key"));
            if (actualHeader == null) {
                fail("Header '%s' is not found in %s", columns.get("header_key"), apiSteps.getResponse().headers());
            }
            assertThat(actualHeader.getValue())
                .describedAs("Value of header '%s' is incorrect.", columns.get("header_key"))
                .isEqualTo(columns.get("header_value"));
        }
    }

    @Then("the response should contain {word} field")
    public void theResponseShouldContain(String jsonPath) {
        var responseBody = getResponseBody();
        assertThat(responseBody.read(jsonPath, String.class))
            .describedAs("Response does NOT contain the given field '%s'\n Response: %s", jsonPath, responseBody.jsonString())
            .isNotNull();
    }

    @Then("the response should NOT contain fields:")
    public void theResponseShouldNOTContain(List<String> jsonPaths) {
        var responseBody = getResponseBody();
        for (String jsonPath : jsonPaths) {
            var fieldValue = responseBody.read(jsonPath);
            assertThat(fieldValue)
                .as("Response should NOT contain field '%s'.", jsonPath)
                .isNull();
        }
    }

    @Then("the response should contain")
    public void theResponseShouldContain(DataTable table) {
        var responseBody = getResponseBody();
        for (Map<String, String> columns : table.entries()) {
            assertThat(responseBody.read(columns.get("json_path"), String.class))
                .describedAs("Value of field '%s' is incorrect.", columns.get("json_path"))
                .isEqualTo(columns.get("value"));
        }
    }

    @Then("the response body should be empty")
    public void theResponseBodyShouldBe() {
        assertThat(apiSteps.getResponse().then().extract().body().asString())
            .describedAs("Response is NOT empty.\n Response: %s", apiSteps.getResponse().then().extract().body().asPrettyString())
            .isEmpty();
    }

    private DocumentContext getResponseBody() {
        return JsonPath.using(jsonPathConfig).parse(apiSteps.getResponse().body().asPrettyString());
    }
}
