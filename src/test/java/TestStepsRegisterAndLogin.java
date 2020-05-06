import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jbehave.core.annotations.*;
import org.jbehave.core.embedder.StoryRunner;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestStepsRegisterAndLogin {

    private static ClientClass client = new ClientClass();
    private StorageClass storage = new StorageClass();
    private static WebDriverClass driver;

    @BeforeStories
    public void beforeStories() {
        if (client.getActive().equalsIgnoreCase("login"))
            driver = new WebDriverClass();
    }

    @Given("user creates and remembers his credentials for country $country")
    public void createAndRememberRegisterData(String country) {
        storage.setRegisterData(new RegisterData(country));
    }

    @When("user is successfully registered with remembered credentials")
    public void correctRegisterRequest() throws URISyntaxException, IOException {
        HttpResponse httpResponse = client.postRequestRegister(client.buildURI("register"), storage.getRegisterData());
        storage.setCurrentResponse(httpResponse);
    }

    @Then("status code is $code")
    public void verifyGoodRequest(int code) throws IOException {
        HttpResponse httpResponse = storage.getCurrentResponse();
        String bodyContent = EntityUtils.toString(httpResponse.getEntity());
        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(code);
        assertThat(client.getResponseMessage(bodyContent, httpResponse.getStatusLine().getStatusCode())).isEqualTo(client.getExpectedMessageByCode(code));
    }

    @When("user enters invalid password $password")
    public void invalidPasswordRequest(String password) throws URISyntaxException, IOException {
        RegisterData registerData = storage.getRegisterData();
        registerData.setPassword(password);
        HttpResponse httpResponse = client.postRequestRegister(client.buildURI("register"), registerData);
        storage.setCurrentResponse(httpResponse);
    }

    @When("user enters invalid username $username")
    public void invalidUsernameRequest(String username) throws URISyntaxException, IOException {
        RegisterData registerData = storage.getRegisterData();
        registerData.setUsername(username);
        HttpResponse httpResponse = client.postRequestRegister(client.buildURI("register"), registerData);
        storage.setCurrentResponse(httpResponse);
    }

    @When("user logs in with $valid credentials")
    public void logInWithValidCredentials(String valid) throws URISyntaxException, IOException {
        RegisterData registerData = storage.getRegisterData();
        if ("invalid".equals(valid))
            registerData.setUsername(UtilsClass.randomizeUsername());
        HttpResponse httpResponse = client.postRequestLogin(client.buildURI("login"), registerData.getUsername(), registerData.getPassword());
        storage.setCurrentResponse(httpResponse);
    }

    @When("user opens email and click activation link")
    public void openEmailAndClickLink() throws InterruptedException {
        RegisterData registerData = storage.getRegisterData();
        driver.openMailPage(UtilsClass.createURLforEmail(registerData.getEmail()));
        driver.openEmailAndClickVerificationLink();
    }

    @AfterStories
    public void closeWebDriver() {
        driver.closeWebDriver();
    }
}
