@update @employee
Feature: Update employee API test suite

  @update-employee-1
  Scenario: Update an existing employee with valid data
    Given I created a new employee
    And I set id path param to the previously received employee id
    And I have a http request body template from create-employee.json with
      | expressions                  |
      | set $.name to %s James Smith |
      | set $.salary to %d 9874      |
      | set $.age to %d 99           |
    When I PUT employees/{id}
    Then status code should be 200
    And the response should contain
      | json_path | value       |
      | $.name    | James Smith |
      | $.salary  | 9874        |
      | $.age     | 99          |


  @update-employee-2
  Scenario: Update an existing employee using explicit given steps
    Given I have a http request body template from create-employee.json with
      | expressions                              |
      | set $.name to %s #{randomAlphabetic(10)} |
      | set $.salary to %d #{randomNumeric(4)}   |
      | set $.age to %d #{randomNumeric(2)}      |
    And I POST /employees
    And I set id path param to the previously received employee id
    And I have a http request body template from create-employee.json with
      | expressions                |
      | set $.name to %s Eva Smith |
      | set $.salary to %d 84613   |
      | set $.age to %d 25         |
    When I PUT employees/{id}
    Then status code should be 200
    And the response should contain
      | json_path | value     |
      | $.name    | Eva Smith |
      | $.salary  | 84613     |
      | $.age     | 25        |