package actions.factoryEnvironment;

import actions.commons.BaseTest;
import actions.commons.GlobalConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SauceLabsFactory extends BaseTest {

    private final String browserName;
    private final String platformName;
    private final String browserVersion;
    public WebDriver driver;

    public SauceLabsFactory(String browserName, String platformName, String browserVersion) {
        this.browserName = browserName;
        this.platformName = platformName;
        this.browserVersion = browserVersion;
    }

    public WebDriver createDriver() {
        DesiredCapabilities caps = new DesiredCapabilities();

        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        caps.setCapability("setBrowserVersion", browserName);
        caps.setCapability("setPlatformName", platformName);
        caps.setCapability("setBrowserVersion", browserVersion);

        Map<String, Object> sauceOptions = new HashMap<>();
        sauceOptions.put("build", "Platform Configurator Build " + currentDate);
        sauceOptions.put("name", "Platform Configurator Job " + currentTime);
        caps.setCapability("sauce:options", sauceOptions);

        try {
            driver = new RemoteWebDriver(new URL(GlobalConstants.SAUCE_URL), caps);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;

    }
}
