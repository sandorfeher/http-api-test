# HTTP-API-TEST-SUPPORT

Provides test automation solution for HTTP API testing with Java, Cucumber, Spring & RestAssured 
with additionally implemented features enabling test automation from Day 1.

## Modules

- http-api-test-bom - Bill Of Materials
- [http-api-test-core](http-api-test-core)

## Getting Started

### Requirements

Be sure to have the following items installed:

- JDK 11
- Maven 3.6.1+

### Technology Stack

- Spring 5.3.15
- Cucumber / Gherkin 7.2.3
- Jayway JsonPath 2.7
- RestAssured 4.5.1
- Allure Report 2.17.3

### Installation

### Maven

To keep version in sync, it is advised to use Bill of Material.

```xml 
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>http.api.test</groupId>
      <artifactId>http-api-test-bom</artifactId>
      <version>1.0-SNAPSHOT</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

Then, to add the HTTP API testing core module, just add it as a dependency:

```xml 
  <dependencies>
    <dependency>
      <groupId>http.api.test</groupId>
      <artifactId>http-api-test-core</artifactId>
    </dependency>
  </dependencies>
```

## Development

Please make sure to
install [Cucumber for Java](https://plugins.jetbrains.com/plugin/7212-cucumber-for-java) plugin in
IntelliJ for easy navigation and test execution.
