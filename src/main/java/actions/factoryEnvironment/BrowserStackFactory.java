package actions.factoryEnvironment;

import actions.commons.BaseTest;
import actions.commons.GlobalConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserStackFactory extends BaseTest {

    private final String browserName;
    private final String osName;
    private final String osVersion;
    private final String browserVersion;
    public WebDriver driver;

    public BrowserStackFactory(String browserName, String osName, String osVersion, String browserVersion) {
        this.browserName = browserName;
        this.osName = osName;
        this.osVersion = osVersion;
        this.browserVersion = browserVersion;
    }

    public WebDriver createDriver() {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("os", osName);
        caps.setCapability("os_version", osVersion);
        caps.setCapability("browser", browserName);
        caps.setCapability("browser_version", browserVersion);
        caps.setCapability("name", "Run");
        caps.setCapability("browserstack.debug", "true");
        caps.setCapability("resolution", "1920x1080");
        caps.setCapability("name", "Run on " + osName + " | " + osVersion + " | " + browserName + " " + DateToString());

        try {
            driver = new RemoteWebDriver(new URL(GlobalConstants.BROWSER_STACK_URL), caps);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }

}
