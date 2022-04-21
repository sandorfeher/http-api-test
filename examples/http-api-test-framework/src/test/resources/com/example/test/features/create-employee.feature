@create @employee
Feature: Create employee API test suite

  @create-employee-1
  Scenario: Create a new employee
    Given I have a http request body from create-employee.json
    When I POST /employees
    Then status code should be 200
    And the response should contain
      | json_path | value          |
      | $.name    | Michael Wilson |

  @create-employee-2
  Scenario: Create a new employee - using expressions
    Given I have a http request body template from create-employee.json with
      | expressions               |
      | set $.name to %s John Doe |
      | set $.salary to %d 4321   |
      | set $.age to %d 20        |
    When I POST /employees
    Then status code should be 200
    And the response should contain
      | json_path | value    |
      | $.name    | John Doe |
      | $.salary  | 4321     |
      | $.age     | 20       |

  @create-employee-3
  Scenario: Create a new employee - using random value placeholders in expressions
    Given I have a http request body template from create-employee.json with
      | expressions                              |
      | set $.name to %s #{randomAlphabetic(10)} |
      | set $.salary to %d #{randomNumeric(4)}   |
      | set $.age to %d #{randomNumeric(2)}      |
    When I POST /employees
    Then status code should be 200

  @create-employee-4
  Scenario: Create a new employee - using json fragments
    Given I have a http request body template from create-employee.json with
      | expressions                                                                  |
      | set $.name to $.employee.name using employee-request-body-fragments.json     |
      | set $.salary to $.employee.salary using employee-request-body-fragments.json |
      | set $.age to $.employee.age using employee-request-body-fragments.json       |
    When I POST /employees
    Then status code should be 200


