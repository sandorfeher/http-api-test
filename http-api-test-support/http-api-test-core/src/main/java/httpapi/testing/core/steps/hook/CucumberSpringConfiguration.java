package httpapi.testing.core.steps.hook;

import httpapi.testing.core.configuration.BaseTestConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = BaseTestConfiguration.class)
public class CucumberSpringConfiguration {
}
