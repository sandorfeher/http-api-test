Feature: Http api request handling

  Scenario: Read http request body json
    Given I have a http request body from sample.json
    Then http request body should contain
      | json_path   | value |
      | $.expensive | 10    |

  Scenario: Resolve http request body template using set expression
    Given I have a http request body template from sample.json with
      | expressions                                  |
      | set $.store.bicycle.color to %s blue         |
      | set $.store.book[1].author to %s James Smith |
      | set $.store.book[1].price to %f 2.55         |
    Then http request body should contain
      | json_path              | value       |
      | $.store.bicycle.color  | blue        |
      | $.store.book[1].author | James Smith |
      | $.store.book[1].price  | 2.55        |

  Scenario: Resolve http request body template using set expression
    Given I have a http request body template from sample.json with
      | expressions                                               |
      | set $.store.bicycle.color to %s #{randomAlphabetic(10)}   |
      | set $.store.book[1].title to %s #{randomAlphaNumeric(15)} |
      | set $.age to %s #{randomNumeric(2)}                       |
    Then http request body field length is:
      | json_path             | value |
      | $.store.bicycle.color | 10    |
      | $.store.book[1].title | 15    |
      | $.age                 | 2     |

  Scenario: Resolve http request body template using set expressions with json-fragments
    Given I have a http request body template from sample.json with
      | expressions                                                             |
      | set $.store.book[4] to $.book using http-request-body-fragments.json    |
      | set $.store.bicycle to $.bicycle using http-request-body-fragments.json |
    Then http request body should contain
      | json_path              | value     |
      | $.store.book.length()  | 5         |
      | $.store.book[4].author | Test Elek |
      | $.store.bicycle.color  | white     |
      | $.store.bicycle.price  | 20.21     |

  Scenario: Resolve http request body template using remove expression
    Given I have a http request body template from sample.json with
      | expressions            |
      | remove $.store.book[0] |
      | remove $.store.bicycle |
    Then http request body should contain
      | json_path             | value |
      | $.store.book.length() | 3     |
    And http request body should NOT contain fields:
      | $.bicycle |