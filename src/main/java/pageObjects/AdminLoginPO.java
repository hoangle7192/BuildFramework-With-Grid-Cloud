package pageObjects;

import actions.commons.BasePage;
import org.openqa.selenium.WebDriver;

public class AdminLoginPO extends BasePage {

    WebDriver driver;

    public AdminLoginPO(WebDriver driver) {
        this.driver = driver;
    }

}
