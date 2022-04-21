package com.example.test.features;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasspathResource("com/example/test")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.example.test, httpapi.testing.core")
public class RunCucumberTest {
}
