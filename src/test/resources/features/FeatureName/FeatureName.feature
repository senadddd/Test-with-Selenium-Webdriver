Feature:Mailchimp

  Scenario Outline: Create user
    Given I start a browser "<Browser>"
    Given I input valid email "<Email>"
    Given I first input username "<Username>"
    Given I then input a valid "<Password>"
    When I click the signup button
    Then I can connect "<Connect>"



    Examples:
      |Browser|Email      |Username     |Password|Connect     |
      |chrome |random     |random       |Tack585.|Yes         |

      |chrome |random     |longUsername |Tack585.|Characters  |
      |chrome |random     |exist        |Tack585.|Exist       |
      |chrome |missing    |random       |Tack585.|MissingEmail|







  #create user ok
  #create user long username 100 and more charters
  #create user user already exist
#create user email missing
















