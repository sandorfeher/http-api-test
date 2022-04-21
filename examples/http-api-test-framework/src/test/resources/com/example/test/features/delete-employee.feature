@delete @employee
Feature: Delete employee API test suite

  @get-employee-by-id
  Scenario: Delete employee by employee id
    Given I created a new employee
    And I set id path param to the previously received employee id
    When I DELETE /employees/{id}
    Then status code should be 204