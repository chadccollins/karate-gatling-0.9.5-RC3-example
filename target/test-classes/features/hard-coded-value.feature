Feature: API Testing

  Background:
    * header Content-Type = "application/json"

  Scenario Outline: Call the API with data hardcoded
    Given url consumerApi.url
    And path "/something/v1", '<id>'
    And header X-MyHeader = "Hello, World"
    When method get
    Then status 200
    And match response.someProperty == false

    Examples:
    | id |
    | 1 |
    | 2 |
    | 3 |
    | 4 |