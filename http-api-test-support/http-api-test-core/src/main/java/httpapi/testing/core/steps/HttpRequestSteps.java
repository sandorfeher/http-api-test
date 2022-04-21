package httpapi.testing.core.steps;

import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import httpapi.testing.core.utils.data.requestbody.RequestBodyModifier;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpRequestSteps {
    private final RequestBodyModifier requestBodyModifier;

    @Getter
    private RequestSpecification request = RestAssured.given();
    @Getter
    private JsonNode requestBody;
    @Getter
    private Response response;

    @Given("I set request headers")
    public void i_set_request_headers(DataTable table) {
        request.with().headers(
            table.entries().stream().collect(Collectors.toMap(
                columns -> columns.get("header_key"),
                columns -> columns.get("header_value"))
            )
        );
    }

    @Given("I set request parameters")
    public void i_set_request_parameters(DataTable table) {
        request.with().params(
            table.entries().stream().collect(Collectors.toMap(
                columns -> columns.get("param_key"),
                columns -> columns.get("param_value"))
            )
        );
    }

    @Given("I set request path params")
    public void iSetRequestPathParams(DataTable table) {
        request.with().pathParams(
            table.entries().stream().collect(Collectors.toMap(
                columns -> columns.get("param_key"),
                columns -> columns.get("param_value"))
            )
        );
    }

    /**
     * RequestSpecification is reset after POST performed.
     **/
    @When("I POST {word}")
    public void i_post(String uri) {
        response = request.when().post(uri);
        resetRequest();
    }

    /**
     * RequestSpecification is reset after GET performed.
     **/
    @When("I GET {word}")
    public void i_get(String uri) {
        response = request.when().get(uri);
        resetRequest();
    }

    /**
     * RequestSpecification is reset after PUT performed.
     **/
    @When("I PUT {word}")
    public void i_put(String uri) {
        response = request.when().put(uri);
        resetRequest();
    }

    /**
     * RequestSpecification is reset after PATCH performed.
     **/
    @When("I PATCH {word}")
    public void i_patch(String uri) {
        response = request.when().patch(uri);
        resetRequest();
    }

    /**
     * RequestSpecification is reset after DELETE performed.
     **/
    @When("I DELETE {word}")
    public void i_delete(String uri) {
        response = request.when().delete(uri);
        resetRequest();
    }

    /**
     * Modifies certain request body field. See {@link RequestBodyModifier#modifyRequestBodyField(JsonNode, String, Object)} for further details how
     * different type of Values are handled.
     *
     * @param jsonPath Path to set
     * @param value    New value
     */
    public void modifyRequestBodyField(String jsonPath, Object value) {
        JsonNode modifiedPayload = requestBodyModifier.modifyRequestBodyField(requestBody, jsonPath, value);
        setRequestBody(modifiedPayload);
    }

    /**
     * Remove certain request body field.
     *
     * @param jsonPath json path for the field to be removed.
     */
    public void removeRequestBodyField(String jsonPath) {
        JsonNode modifiedPayload = requestBodyModifier.removeRequestBodyField(requestBody, jsonPath);
        setRequestBody(modifiedPayload);
    }

    /**
     * Specify the request body that will be used for each request.
     *
     * @param requestBody request body as JsonNode
     */
    public void setRequestBody(JsonNode requestBody) {
        this.requestBody = requestBody;
        request.body(requestBody);
    }

    /**
     * Reset RequestSpecification.
     */
    public void resetRequest() {
        request = RestAssured.given();
    }

}
