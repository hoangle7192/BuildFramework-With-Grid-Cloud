package actions.commons;

import java.io.File;

public class GlobalConstants {

    public static final String PROJECT_PATH = System.getProperty("user.dir");

    public static final String ADBLOCK_EXTENSION_OF_CHROME = PROJECT_PATH + File.separator + "browserExtensions" + File.separator + "Ublock_Chrome.crx";
    public static final String ADBLOCK_EXTENSION_OF_FIREFOX = PROJECT_PATH + File.separator + "browserExtensions" + File.separator + "adblocker-firefox.xpi";

    public static final String DOWNLOAD_FOLDER_PATH = PROJECT_PATH + File.separator + "downloadFiles";
    public static final String UPLOAD_FILE_FOLDER = PROJECT_PATH + File.separator + "uploadFiles" + File.separator;
    public static final String BROWSER_LOG_PATH = PROJECT_PATH + File.separator + "browserLogs" + File.separator;

    public static final String API_TEST_DATA_FOLDER = PROJECT_PATH + File.separator + "src" + File.separator + "main" + File.separator + "java"
            + File.separator + "testData" + File.separator + "API" + File.separator;

    public static final String JAVA_VERSION = System.getProperty("java.version");

    public static final String BROWSER_USERNAME = "hoanglehuy_9mbe1j";
    public static final String BROWSER_ACCESS_KEY = "aXYa5mwfsPchL2pRN6LJ";
    public static final String BROWSER_STACK_URL = "https://" + BROWSER_USERNAME + ":" + BROWSER_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";

    public static final String SAUCE_USERNAME = "oauth-hoanglh2-fdebe";
    public static final String SAUCE_ACCESS_KEY = "8d7f0d53-d001-4b29-9795-35086ddd2fd1";
    public static final String SAUCE_URL = "https://" + SAUCE_USERNAME + ":" + SAUCE_ACCESS_KEY + "@ondemand.eu-central-1.saucelabs.com:443/wd/hub";


    public static final int LONG_TIME_OUT = 30;
    public static final int SHORT_TIME_OUT = 10;

}
