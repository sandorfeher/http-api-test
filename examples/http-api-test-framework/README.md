# TEST FRAMEWORK
An example test automation framework solution based on Java, Cucumber, Spring
using a custom, additionally developed [http-api-test-core](/http-api-test-support/http-api-test-core) library, showcasing its capabilities.

Includes:
* [System Under Test (SUT)](/examples/employee-rest-app) - Spring boot application used to demonstrate the framework capabilities,
* Extended test execution reporting with Cucumber and Allure Report,
* Configuration management to support the execution of various test environments in parallel,
* Parallel execution capability to speed up test execution.

## Getting Started

### Requirements

Be sure to have the following items installed:

- JDK 17
- Maven 3.6.1+

## Installation

Perform `mvn clean install` on `http-api-test-support library` so that it will be available within your
local maven repository.

## Usage

### Test execution

The framework builds
on [Cucumber](https://github.com/cucumber/cucumber-jvm/tree/main/junit-platform-engine).

By default, Cucumber runs tests sequentially in a single thread. Running tests in parallel is
available as an opt-in feature. To enable parallel execution, set the
`cucumber.execution.parallel.enabled` configuration parameter to `true`, e.g. in
`junit-platform.properties`.

For further details please
refer to [Parallel execution](https://github.com/cucumber/cucumber-jvm/tree/main/junit-platform-engine#parallel-execution).

Test execution is triggered by `mvn test` command. It is recommended to execute it with `clean` to
clear previous test results.

### Employee Spring boot based REST API
To run the provided Spring boot app [employee-rest-app](/examples/employee-rest-app) 
 
Navigate to it's root folder and perform
```
mvn clean install
mvn spring-boot:run
```
Then the app should run on localhost:8081. Supported endpoints are available under http://localhost:8081/api

For further details please refer to: [Employee REST API](/examples/employee-rest-app/README.md)

### Scenario execution
Run full suite:

```
mvn clean test
```

Run tests on specific environment
```
mvn test -Dintegration.test.env=local
```

Be noted tests tagged with @wip and @disabled are skipped by default from test execution.

To run a subset of tests utilizing tags with -Dcucumber.filter.tags:

* Run scenarios matching one tag:

```
mvn clean test -Dcucumber.filter.tags="@feature_1"
```

* Run scenarios matching both tags (AND condition):

```
mvn clean test -Dcucumber.filter.tags="@feature_1 and @api_scenario_1"
```

* Run scenarios matching at least one tag (OR condition):

```
mvn clean test -Dcucumber.filter.tags="@api_scenario_2 or @api_scenario_1"
```

* Run scenarios not matching tag:

```
mvn clean test -Dcucumber.filter.tags="not @api"
```

### Generating test report

After a test execution various reports are generated:

Cucumber:

- Can be specified by `cucumber.plugin=pretty` property
- The report should be available at `target/cucumber-pretty.html`

Allure:

- Allure results should be available under `target/allure-results`
- To generate HTML report simply run `mvn io.qameta.allure:allure-maven:report`
- The report should be available at `target/site/index.html`

Allure documentation - [Build configuration](https://docs.qameta.io/allure/#_cucumber_jvm).
