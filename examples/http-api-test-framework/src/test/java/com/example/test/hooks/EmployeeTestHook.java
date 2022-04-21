package com.example.test.hooks;

import httpapi.testing.core.steps.HttpRequestSteps;
import io.cucumber.java.Before;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@RequiredArgsConstructor
public class EmployeeTestHook {
    private final HttpRequestSteps apiSteps;

    @Value("${BASE_URL}")
    private String baseUrl;

    @Before
    public void start() {
        LOG.info("Running tests against {}", baseUrl);
        setEmployeeAPIRequestSpecification();
    }

    public void setEmployeeAPIRequestSpecification() {
        apiSteps.getRequest().spec(new RequestSpecBuilder()
            .setBaseUri(baseUrl)
            .setContentType(ContentType.JSON)
            .addFilter(new AllureRestAssured())
            .build());
    }

}
