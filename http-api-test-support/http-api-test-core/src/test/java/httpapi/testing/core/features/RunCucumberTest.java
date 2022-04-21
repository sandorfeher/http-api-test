package httpapi.testing.core.features;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasspathResource("httpapi/testing/core")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "httpapi.testing.core")
public class RunCucumberTest {
}
