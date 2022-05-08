package actions.factoryEnvironment;

import actions.factoryBrowser.*;
import enums.BrowserNameList;
import org.openqa.selenium.WebDriver;

public class LocalFactory {

    private final String browserName;
    public WebDriver driver;

    public LocalFactory(String browserName) {
        this.browserName = browserName;
    }

    public WebDriver createDriver() {
        BrowserNameList browser = BrowserNameList.valueOf(browserName.toUpperCase());

        switch (browser) {
            case CHROME:
                driver = new ChromeDriverManager().getBrowserDriver();
                break;
            case FIREFOX:
                driver = new FirefoxDriverManager().getBrowserDriver();
                break;
            case EDGE:
                driver = new EdgeDriverManager().getBrowserDriver();
                break;
            case IE:
                driver = new IEDriverManager().getBrowserDriver();
                break;
            case SAFARI:
                driver = new SafariDriverManager().getBrowserDriver();
                break;
            case H_CHROME:
                driver = new HeadLessChromeDriverManager().getBrowserDriver();
                break;
            case H_FIREFOX:
                driver = new HeadLessFirefoxDriverManager().getBrowserDriver();
                break;
            default:
                throw new BrowserNotSupportedException(browserName);
        }
        return driver;
    }
}
