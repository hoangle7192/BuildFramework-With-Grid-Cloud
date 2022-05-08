package actions.commons;

import actions.factoryEnvironment.BrowserStackFactory;
import actions.factoryEnvironment.GridFactory;
import actions.factoryEnvironment.LocalFactory;
import enums.BrowserNameList;
import enums.EnvNameList;
import enums.RoleNameList;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import propertiesConfig.ConfigProperties;
import propertiesConfig.Environment;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class BaseTest {

    private WebDriver driver;
    protected final Log log;
    private final ChromeOptions chromeOptions = new ChromeOptions();
    private final FirefoxOptions firefoxoptions = new FirefoxOptions();

    protected BaseTest() {
        log = LogFactory.getLog(getClass());
    }

    protected String getUrlFromRoleName(String roleName) {
        String url = null;
        Environment environment = ConfigProperties.getEnvironment();

        RoleNameList role = RoleNameList.valueOf(roleName.toUpperCase());
        if (role == RoleNameList.ADMIN) {
            url = environment.getUrlAdmin();
        } else if (role == RoleNameList.USER) {
            url = environment.getUrlUser();
        }
        return url;
    }

    protected WebDriver getBrowserDriver(String envName, String roleName, String browserName, String ipAddress, String portName, String osName, String osVersion, String browserVersion) {
        EnvNameList env = EnvNameList.valueOf(envName.toUpperCase());

        switch (env) {
            case LOCAL:
                driver = new LocalFactory(browserName).createDriver();
                break;
            case GRID:
                driver = new GridFactory(browserName, ipAddress, portName).createDriver();
                break;
            case BROWSERSTACK:
                driver = new BrowserStackFactory(browserName, osName, osVersion, browserVersion).createDriver();
                break;
            default:
                throw new RuntimeException("This envName: " + env + "is not support");
        }

        driver.manage().timeouts().implicitlyWait(GlobalConstants.LONG_TIME_OUT, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(getUrlFromRoleName(roleName));
        return driver;
    }

    protected WebDriver getBrowserDriverLocal(String browserName, String url) {
        BrowserNameList browser = BrowserNameList.valueOf(browserName.toUpperCase());
        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                chromeOptions.addArguments("--disable-norifications");
                chromeOptions.addExtensions(new File(GlobalConstants.ADBLOCK_EXTENSION_OF_CHROME));
                driver = new ChromeDriver(chromeOptions);
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                firefoxoptions.addPreference("dom.webnotifications.enabled", false);
                FirefoxProfile profile = new FirefoxProfile();
                profile.addExtension(new File(GlobalConstants.ADBLOCK_EXTENSION_OF_FIREFOX));
                driver = new FirefoxDriver(firefoxoptions);
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                driver = new EdgeDriver(edgeOptions);
                break;
            case H_CHROME:
                WebDriverManager.chromedriver().setup();
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("window-size=1920x1080");
                driver = new ChromeDriver(chromeOptions);
                break;
            case H_FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                firefoxoptions.addArguments("--headless");
                firefoxoptions.addArguments("window-size=1920x1080");
                driver = new FirefoxDriver(firefoxoptions);
                break;
            default:
                throw new RuntimeException("This Browser Is Not Support");
        }
        driver.manage().timeouts().implicitlyWait(GlobalConstants.LONG_TIME_OUT, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(url);
        return driver;
    }

    protected WebDriver getBrowserDriverGrid(String browserName, String url, String ipAddress, String portName) {
        BrowserNameList browser = BrowserNameList.valueOf(browserName.toUpperCase());
        DesiredCapabilities capability = null;

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
                throw new RuntimeException("This Browser Is Not Support");
        }

        try {
            driver = new RemoteWebDriver(new URL(String.format("http://%s:%s/wd/hub", ipAddress, portName)), capability);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        driver.manage().timeouts().implicitlyWait(GlobalConstants.LONG_TIME_OUT, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(url);
        return driver;
    }

    protected WebDriver getBrowserDriverBrowserStack(String browserName, String url, String osName, String osVersion, String browserVersion) {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("os", osName);
        caps.setCapability("os_version", osVersion);
        caps.setCapability("browser", browserName);
        caps.setCapability("browser_version", browserVersion);
        caps.setCapability("name", "Run");
        caps.setCapability("browserstack.debug", "true");
        caps.setCapability("resolution", "1920x1080");
        caps.setCapability("name", "Run on" + osName + " | " + osVersion + " | " + browserName + DateToString());

        try {
            driver = new RemoteWebDriver(new URL(GlobalConstants.BROWSER_STACK_URL), caps);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        driver.manage().timeouts().implicitlyWait(GlobalConstants.LONG_TIME_OUT, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(url);
        return driver;
    }

    protected WebDriver getBrowserDriverSauceLabs(String browserName, String url, String platform, String version) {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("name", "Run on " + browserName + " | " + platform + " | " + version + " | " + DateToString());
        caps.setCapability("browserName", browserName);
        caps.setCapability("platform", platform);
        caps.setCapability("version", version);

        Map<String, Object> sauceOptions = new HashMap<>();
        if (platform.contains("Windows")) {
            sauceOptions.put("screenResolution", "1920x1080");
        } else {
            sauceOptions.put("screenResolution", "1920x1440");
        }
        caps.setCapability("sauce:options", sauceOptions);

        try {
            driver = new RemoteWebDriver(new URL(GlobalConstants.SAUCE_URL), caps);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        driver.manage().timeouts().implicitlyWait(GlobalConstants.LONG_TIME_OUT, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(url);
        return driver;
    }

    public WebDriver getDriverInstance() {
        return this.driver;
    }

    public int generateNumber() {
        Random random = new Random();
        return random.nextInt(999999);
    }

    /*Hàm Assert*/
    private boolean checkTrue(boolean condition) {
        boolean pass = true;
        try {
            Assert.assertTrue(condition);
            log.info(" -------------------------- PASSED -------------------------- ");
        } catch (Throwable e) {
            log.info(" -------------------------- FAILED -------------------------- ");
            pass = false;

            // Add lỗi vào ReportNG
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyTrue(boolean condition) {
        return checkTrue(condition);
    }

    private boolean checkFailed(boolean condition) {
        boolean pass = true;
        try {
            Assert.assertFalse(condition);
            log.info(" -------------------------- PASSED -------------------------- ");
        } catch (Throwable e) {
            log.info(" -------------------------- FAILED -------------------------- ");
            pass = false;

            // Add lỗi vào ReportNG
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyFalse(boolean condition) {
        return checkFailed(condition);
    }

    private boolean checkEquals(Object actual, Object expected) {
        boolean pass = true;
        try {
            Assert.assertEquals(actual, expected);
            log.info(" -------------------------- PASSED -------------------------- ");
        } catch (Throwable e) {
            pass = false;
            log.info(" -------------------------- FAILED -------------------------- ");

            // Add lỗi vào ReportNG
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyEquals(Object actual, Object expected) {
        return checkEquals(actual, expected);
    }
    /*Hàm Assert*/

    protected String DateToString() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }

    protected void closeBrowserAndDriver(String envName) {
        if (envName.equals("LOCAL") || envName.equals("GRID")) {
            String cmd = "";
            try {
                String osName = System.getProperty("os.name").toLowerCase();
                log.info("OS name = " + osName);

                String driverInstanceName = driver.toString().toLowerCase();
                log.info("Driver instance name = " + driverInstanceName);

                if (driverInstanceName.contains("chrome")) {
                    if (osName.contains("window")) {
                        cmd = "taskkill /F /FI \"IMAGENAME eq chromedriver*\"";
                    } else {
                        cmd = "pkill chromedriver";
                    }
                } else if (driverInstanceName.contains("internetexplorer")) {
                    if (osName.contains("window")) {
                        cmd = "taskkill /F /FI \"IMAGENAME eq IEDriverServer*\"";
                    }
                } else if (driverInstanceName.contains("firefox")) {
                    if (osName.contains("windows")) {
                        cmd = "taskkill /F /FI \"IMAGENAME eq geckodriver*\"";
                    } else {
                        cmd = "pkill geckodriver";
                    }
                } else if (driverInstanceName.contains("edge")) {
                    if (osName.contains("window")) {
                        cmd = "taskkill /F /FI \"IMAGENAME eq msedgedriver*\"";
                    } else {
                        cmd = "pkill msedgedriver";
                    }
                } else if (driverInstanceName.contains("opera")) {
                    if (osName.contains("window")) {
                        cmd = "taskkill /F /FI \"IMAGENAME eq operadriver*\"";
                    } else {
                        cmd = "pkill operadriver";
                    }
                } else if (driverInstanceName.contains("safari")) {
                    if (osName.contains("mac")) {
                        cmd = "pkill safaridriver";
                    }
                }

                if (driver != null) {
                    driver.manage().deleteAllCookies();
                    driver.quit();
                }
            } catch (Exception e) {
                log.info(e.getMessage());
            } finally {
                try {
                    Process process = Runtime.getRuntime().exec(cmd);
                    process.waitFor();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (driver != null) {
                driver.manage().deleteAllCookies();
                driver.quit();
            }

        }
    }
}
