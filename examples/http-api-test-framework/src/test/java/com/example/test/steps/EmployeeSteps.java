package com.example.test.steps;

import com.example.test.hooks.EmployeeTestHook;
import httpapi.testing.core.steps.HttpRequestBodySteps;
import httpapi.testing.core.steps.HttpRequestSteps;
import httpapi.testing.core.steps.HttpResponseVerificationSteps;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@RequiredArgsConstructor
public class EmployeeSteps {
    private final HttpRequestSteps requestSteps;
    private final HttpRequestBodySteps requestBodySteps;
    private final EmployeeTestHook employeeTestHook;
    private final HttpResponseVerificationSteps verificationSteps;

    @Given("I created a new employee")
    public void createEmployee() {
        requestBodySteps.getHttpRequestBodyTemplateFrom("create-employee.json");
        requestSteps.modifyRequestBodyField("$.name", RandomStringUtils.randomAlphabetic(10));
        requestSteps.modifyRequestBodyField("$.salary", RandomStringUtils.randomNumeric(5));
        requestSteps.modifyRequestBodyField("$.age", RandomStringUtils.randomNumeric(2));
        requestSteps.i_post("/employees");
        verificationSteps.thenStatusCodeShouldBe(200);
    }

    @Given("I set id path param to the previously received employee id")
    public void iSetEmployeeIdPathParam() {
        employeeTestHook.setEmployeeAPIRequestSpecification();
        int id = requestSteps.getResponse().getBody().jsonPath().get("id");
        requestSteps.getRequest().pathParams("id", id);
    }

}
