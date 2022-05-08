package actions.factoryEnvironment;

import enums.BrowserNameList;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class GridFactory {

    private final String browserName;
    private final String ipAddress;
    private final String portName;
    public WebDriver driver;

    public GridFactory(String browserName, String ipAddress, String portName) {
        this.browserName = browserName;
        this.ipAddress = ipAddress;
        this.portName = portName;
    }

    public WebDriver createDriver() {
        BrowserNameList browser = BrowserNameList.valueOf(browserName.toUpperCase());
        DesiredCapabilities capability =  null;

        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                capability = DesiredCapabilities.chrome();
                capability.setBrowserName("chrome");
                capability.setPlatform(Platform.ANY);

                ChromeOptions cOptions = new ChromeOptions();
                cOptions.merge(capability);
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                capability = DesiredCapabilities.firefox();
                capability.setBrowserName("firefox");
                capability.setPlatform(Platform.ANY);

                FirefoxOptions fOptions = new FirefoxOptions();
                fOptions.merge(capability);
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                capability = DesiredCapabilities.edge();
                capability.setBrowserName("edge");
                capability.setPlatform(Platform.ANY);
                capability.setJavascriptEnabled(true);
                break;
            case SAFARI:
                capability = DesiredCapabilities.safari();
                capability.setBrowserName("safari");
                capability.setPlatform(Platform.MAC);
                capability.setJavascriptEnabled(true);
                break;
            case IE:
                WebDriverManager.iedriver().arch32().setup();
                capability = DesiredCapabilities.internetExplorer();
                capability.setBrowserName("internetexplorer");
                capability.setPlatform(Platform.ANY);
                capability.setJavascriptEnabled(true);
                break;
            default:
                throw new RuntimeException("This Browser " + browser + "Is Not Support");
        }

        try {
            driver = new RemoteWebDriver(new URL(String.format("http://%s:%s/wd/hub", ipAddress, portName)), capability);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return driver;
    }
}
