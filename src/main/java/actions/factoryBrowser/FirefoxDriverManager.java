package actions.factoryBrowser;

import actions.commons.GlobalConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;

public class FirefoxDriverManager implements BrowserFactory {

    @Override
    public WebDriver getBrowserDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();

        // disable show log
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, GlobalConstants.BROWSER_LOG_PATH + "firefox.log");
        // setting vi tri download file
        options.addPreference("browser.download.folderList", 2);
        options.addPreference("browser.download.dir", GlobalConstants.DOWNLOAD_FOLDER_PATH);
        options.addPreference("browser.download.userDownloadDir", true);
        options.addPreference("browser.helperApps.neverAsk.saveToDisk", "multipart/x-zip, application/zip, application/x-zip-compressed, application/x-compressed, application/msword, " + "application/csv, text/csv, image/png, image/jpeg, application/pdf, text/html, text/plain, application/excel, application/vnd.ms-excel. application/x-excel, " + "application/x-msexcel, application/octet-stream");
        options.addPreference("pdfjs.disable", true);
        // che do an danh
        //options.addArguments("--private");
        // add Extensions
        FirefoxProfile profile = new FirefoxProfile();
        profile.addExtension(new File(GlobalConstants.ADBLOCK_EXTENSION_OF_FIREFOX));

        return new FirefoxDriver(options);
    }

}
