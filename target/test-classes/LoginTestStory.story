Meta:

Narrative:
As a user I want register and then login to the application.

Scenario: Successful login with saved credentials
Given user creates and remembers his credentials for country Switzerland
When user is successfully registered with remembered credentials
And user opens email and click activation link
And user logs in with remembered credentials
Then status code is 200

Scenario: Unknown user tries to log in and error is returned
When user logs in with invalid credentials
Then status code is 401