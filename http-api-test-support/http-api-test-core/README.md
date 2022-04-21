# HTTP-API-TEST-CORE

This is the core project of the test automation framework solution that contains all the main features, like:

- Pre-defined Gherkin Cucumber step definitions for managing the request/response related actions,
- Test data management with templating mechanism (expressions),
- Parallel execution capability.

These core features are available in this separated module, reducing maintainability & supporting the reusability of the implementation.

Please find the available step definitions [here](src/main/java/httpapi/testing/core/steps)
## Examples

```gherkin
Scenario: GET all employees
  When I GET /employees
  Then status code should be 200
```

```gherkin
Scenario: GET employee by id
  Given I set request path params
    | param_key     | param_value |
    | employeeId    | 1           |
  When I GET /employees/{employeeId}
  Then status code should be 200
  And http response body should contain
    | json_path       | value       |
    | $.employee.name | James Smith |
```

```gherkin
Scenario: Create a new employee
  Given I have a http request body from employee.json
  When I POST /create
  Then status code should be 200
  And http response body should contain
    | json_path       | value       |
    | $.employee.name | Thomes John |
```

```gherkin
Scenario: Create a new employee - using expressions
  Given I have a http request body template from employee.json with
    | expressions                  |
    | set $.age to %d 34           |
    | set $.name to %s James Smith |
  When I POST /create
  Then status code should be 200
  And http response body should contain
    | json_path       | value       |
    | $.employee.age  | 34          |
    | $.name          | James Smith |
```

```gherkin
Scenario: Create a new employee - using json fragments
  Given I have a http request body template from employee.json with
    | expressions                                                     |
    | set $.address to $.monaco-address using playload-fragments.json |
  When I POST /create
  Then status code should be 200
  And http response body should contain
    | json_path                  | value  |
    | $.employee.address.country | Monaco |
```

```gherkin
Scenario: Create a new employee - using random value placeholders in expressions
  Given I have a http request body template from create-employee.json with
    | expressions                              |
    | set $.name to %s #{randomAlphabetic(10)} |
    | set $.salary to %d #{randomNumeric(4)}   |
    | set $.age to %d #{randomNumeric(2)}      |
  When I POST /employees
  Then status code should be 200
```

Please find further examples [here](/examples/http-api-test-framework/src/test/resources/com/example/test/features)

## Test Data Management

JSON files can be used as an input for the requests: to parse a http request json body, simply use one of the pre-defined step definitions from the library:

```gherkin
 Given I have a http request body template from {} with
    |expressions |
    | ...        |
```
```gherkin
 Given I have a http request body from {}
```

By default, json files are parsed from the `resources/` directory. To avoid noisy and long step definitions by
adding full classpath, simply put `test-resource-config.properties` under `resources/`
with the following properties to specify the root directories accordingly.

```properties
http.request.body.root.resource.directory=
http.request.body.fragment.root.resource.directory=
```

Then json files can be referred using relative path:

```gherkin 
Given I have a http request body from /relative-path/request-body.json
```

```gherkin 
Given I have a http request body template from /relative-path/request-body.json with
  | modifier expressions              |
  | ... /relative-path/fragment.json  |
```

## Supported test configuration properties

| name                                                 | description                                                      | default value |
|------------------------------------------------------|------------------------------------------------------------------|---------------|
| `http.request.body.root.resource.directory`          | Specifies request body root directory                            | `resources/`  |
| `http.request.body.fragment.root.resource.directory` | Specifies request body fragment root directory                   | `resources/`  |
| `environment.root.resource.directory`                | Specifies the environment property files root directory          | `resources/`  |
| `integration.test.env`                               | Specifies the environment property. `.property` will be ammended | `local`       |

### Request body templating

Modifier expression can be used when loading http request body. It allows to modify the request body,
eliminating the need to create a JSON for every request that is any different from the original.
```gherkin
Given I have a http request body template from {word} with
  | expressions |
  | ...         |
  | ...         |
```


### Supported Expressions
Pattern
`^(?<action>set|remove) (?<jsonPath>\S+)($| to ((?<simpleValue>(?<type>%s|%d|%f) (?<value>.+)$)|(?<pointer>(?<referencePath>.+) using (?<fragmentJsonRelativeFilePath>.+)$)))`.

| Type                          | Description                                                               | Example                                                   |
|:------------------------------|:--------------------------------------------------------------------------|-----------------------------------------------------------|
| Set a field - simple value    | `Insert/update` the `value` at the given `jsonPath`                       | `set` `$.xyz` to `new-value`                              |
| Set a field - reference value | `Insert/update` the `value` at the given `jsonPath` using a json fragment | `set` `$.abc` to `$.reference-path` using `fragment.json` |
| Remove a field                | `Remove` the given path                                                   | `remove` `$.qwe`                                          |

### Supported value placeholders
Pattern
`^#\{(?<type>randomAlphabetic|randomAlphaNumeric|randomNumeric)\((?<length>[0-9]*)\)}$`.

| Placeholder                | Description                                                                                                                                                                      | Example                                                   |
|:---------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------|
| #{randomAlphabetic(int)}   | Creates a random string whose length is the number of characters specified. Characters will be chosen from the set of Latin alphabetic characters (a-z, A-Z).                    | `set $.store.book[1].title to %s #{randomAlphabetic(10)}` |
| #{randomAlphaNumeric(int)} | Creates a random string whose length is the number of characters specified. Characters will be chosen from the set of Latin alphabetic characters (a-z, A-Z) and the digits 0-9. | `set $.description to %s #{randomAlphaNumeric(15)}`       |
| #{randomNumeric(int)}      | Creates a random string whose length is the number of characters specified. Characters will be chosen from the set of numeric characters.                                        | `set $.age to %s #{randomNumeric(2)}`                     |


## Installation

Please find usage example
in [example-test-framework](/examples/http-api-test-framework).

### Maven

```xml 
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>http.api.test</groupId>
      <artifactId>http-api-test-bom</artifactId>
      <version>{http-api-test-library.version}</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
<dependencies>

<dependency>
  <groupId>http.api.test</groupId>
  <artifactId>http-api-test-core</artifactId>
</dependency>
```

Please find detailed
in [pom.xml](/examples/http-api-test-framework/pom.xml)
with surefire, allure report build configuration.

## Cucumber

In order to get the benefits from the predefined step definitions, request templating features etc. from this core module, the glue scope
has to be extended with `httpapi.testing.core`.

For instance:

```java 
...
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "{...},httpapi.testing.core")
public class CucumberTest {
}
```

For further information like how to set up a cucumber-jvm spring project please refer
to [Cucumber documentation](https://github.com/cucumber/cucumber-jvm/blob/main/spring/README.md).

## RestAssured

The provided HTTP api step definitions using [RestAssured](https://github.com/rest-assured/rest-assured).

To start testing your API `io.restassured.specification.RequestSpecification` has to be set
with `httpapi.core.steps.HttpRequestSteps.getRequest()`.

It is recommended to set it in `Before` hook.

For instance:

```java 
...
public class ServiceUnderTestHook {
    @Autowired
    private final HttpRequestSteps apiSteps;

    @Before
    public void start() {
        apiSteps.getRequest().spec(baseConfig);
    }

    private final RequestSpecification baseConfig = new RequestSpecBuilder()
        .setBaseUri(...)
        .setAccept(...)
        .build();
}
```

Please find example hook
here: [SUT hook](/examples/http-api-test-framework/src/test/java/com/example/test/hooks/ServiceUnderTestHook.java).
