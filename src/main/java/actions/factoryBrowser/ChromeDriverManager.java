package actions.factoryBrowser;

import actions.commons.GlobalConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChromeDriverManager implements BrowserFactory {

    @Override
    public WebDriver getBrowserDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        // disable info bar
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);

        //notifications
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-geolocation");

        // disable save password
        options.addArguments("--start-maximized");
        options.addArguments("--disable-web-security");
        options.addArguments("--no-proxy-server");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        //options.addArguments("--incognito"); // che do an danh

        // setting vi tri download file
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", GlobalConstants.DOWNLOAD_FOLDER_PATH);
        options.setExperimentalOption("prefs", chromePrefs);

        // add Extensions
        options.addExtensions(new File(GlobalConstants.ADBLOCK_EXTENSION_OF_CHROME));

        // add disable private SSL
        options.setAcceptInsecureCerts(true);

        // disable show log
        System.setProperty("webdriver.chrome.args", "--disable-logging");
        System.setProperty("webdriver.chrome.silentOutput", "true");

        return new ChromeDriver(options);
    }

}
