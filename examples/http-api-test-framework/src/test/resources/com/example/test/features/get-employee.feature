@get @employee
Feature: Get employee API test suite

  @get-all-employee
  Scenario: GET all employees
    When I GET /employees
    Then status code should be 200

  @get-employee-by-id
  Scenario: GET employee by using employee id
    Given I set request path params
      | param_key | param_value |
      | id        | 1           |
    When I GET /employees/{id}
    Then status code should be 200
    Then the response should contain
      | json_path | value |
      | $.id      | 1     |

  @get-employee-by-name
  Scenario: GET employees by name
    Given I set request path params
      | param_key | param_value |
      | name      | James Smith |
    When I GET /employees/name/{name}
    Then status code should be 200
    Then the response should contain
      | json_path      | value       |
      | $.data[0].name | James Smith |