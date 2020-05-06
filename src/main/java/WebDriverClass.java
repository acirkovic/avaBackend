import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebDriverClass {

    private ChromeDriver webDriver;
    private final String pathToDriver = "src/main/resources/chromedriver";
    private WebDriverWait wait;
    private final String emailSubject = "Just one more tap to get started with Ava";

    /**
     * Web elements - I would use POM but for simplicity I will just add a few here
     */

    By clickConfirmButton = By.xpath("//*[@class='kmButton']");
    By avaEmails = By.xpath("//a[contains(text(), '" + emailSubject + "')]");
    By goBackButton = By.xpath("//span[contains(@class, 'goback')]");
    By avaConfirmationFooter = By.xpath("//span[contains(@class, 'www-avawomen-com')]");

    WebDriverClass() {
        System.setProperty("webdriver.chrome.driver", pathToDriver);
        webDriver = new ChromeDriver();
        wait = new WebDriverWait(webDriver, 10);
    }

    public void openMailPage(String url) {
        webDriver.get(url);
        wait.until(ExpectedConditions.titleContains("1sec MAIL"));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(avaEmails));
//        wait.until(ExpectedConditions.visibilityOfAllElements(avaEmails));
    }

    public void openEmailAndClickVerificationLink() {
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        webDriver.get(webDriver.findElement(avaEmails).getAttribute("href"));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(goBackButton));
        webDriver.switchTo().frame("messageiframe");
        String confirmationLink = webDriver.findElement(clickConfirmButton).getAttribute("href");
        System.out.println(confirmationLink);
        webDriver.get(confirmationLink);
        wait.until(ExpectedConditions.titleContains("Account activated"));
    }

    public void closeWebDriver() {
        webDriver.quit();
    }
}
