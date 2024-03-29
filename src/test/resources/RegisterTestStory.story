Narrative:
As a user
I want to register on Ava platform

Scenario: user is registered with valid credentials
Given user creates and remembers his credentials for country Switzerland
When user is successfully registered with remembered credentials
Then status code is 201

Scenario: user enters invalid password and receives Bad request response
Given user creates and remembers his credentials for country Switzerland
When user enters invalid password 123abc
Then status code is 400

Scenario: user enters invalid username and receives Bad request response
Given user creates and remembers his credentials for country Switzerland
When user enters invalid username Andrija!!@
Then status code is 400