package testcases.RunForGrid;

import actions.commons.BaseTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import reportConfig.ExtentTestManager;

import java.lang.reflect.Method;
import java.util.Random;

public class RunBrowserStack extends BaseTest {
    WebDriver driver;
    Select select;
    String firstName, lastName, email, companyName, password;

    @Parameters({"browser", "url", "osName", "osVersion", "browserVersion"})
    @BeforeClass
    public void beforeClass(String browserName, String url, String osName, String osVersion, String browserVersion) {
        driver = getBrowserDriverBrowserStack(browserName, url, osName, osVersion, browserVersion);

        firstName = "Tony";
        lastName = "Buoi Sang";
        email = "tonybuoisang" + getRandomNumber() + "@hotmail.com";
        companyName = "Tony Buoi Sang";
        password = "tonybuoisang";
    }

    @Test
    public void TC_01_Register(Method method) {

        ExtentTestManager.startTest(method.getName(), "TC_01");

        ExtentTestManager.getTest().log(Status.INFO, "Step01");
        driver.findElement(By.className("ico-register")).click();

        ExtentTestManager.getTest().log(Status.INFO, "Step02");
        driver.findElement(By.id("gender-male")).click();
        sleepInSecond(2);

        ExtentTestManager.getTest().log(Status.INFO, "Step03");
        driver.findElement(By.id("FirstName")).sendKeys(firstName);

        ExtentTestManager.getTest().log(Status.INFO, "Step04");
        driver.findElement(By.id("LastName")).sendKeys(lastName);

        ExtentTestManager.getTest().log(Status.INFO, "Step05");
        select = new Select(driver.findElement(By.name("DateOfBirthDay")));
        select.selectByVisibleText("10");

        ExtentTestManager.getTest().log(Status.INFO, "Step06");
        select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
        select.selectByVisibleText("August");

        ExtentTestManager.getTest().log(Status.INFO, "Step07");
        select = new Select(driver.findElement(By.name("DateOfBirthYear")));
        select.selectByVisibleText("1960");

        ExtentTestManager.getTest().log(Status.INFO, "Step08");
        driver.findElement(By.id("Email")).sendKeys(email);

        ExtentTestManager.getTest().log(Status.INFO, "Step09");
        driver.findElement(By.id("Company")).sendKeys(companyName);

        ExtentTestManager.getTest().log(Status.INFO, "Step10");
        driver.findElement(By.id("Password")).sendKeys(password);

        ExtentTestManager.getTest().log(Status.INFO, "Step11");
        driver.findElement(By.id("ConfirmPassword")).sendKeys(password);

        ExtentTestManager.getTest().log(Status.INFO, "Step12");
        driver.findElement(By.id("register-button")).click();
        sleepInSecond(2);

        ExtentTestManager.getTest().log(Status.INFO, "Step13");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='page registration-result-page']//div[@class='result']")).getText(), "Your registration completed");

        ExtentTestManager.getTest().log(Status.INFO, "Step14");
        driver.findElement(By.className("ico-logout")).click();
        sleepInSecond(2);
    }

    @Test
    public void TC_02_Login(Method method) {

        ExtentTestManager.startTest(method.getName(), "TC_02");

        ExtentTestManager.getTest().log(Status.INFO, "Step01");
        driver.findElement(By.className("ico-login")).click();
        sleepInSecond(2);

        ExtentTestManager.getTest().log(Status.INFO, "Step02");
        driver.findElement(By.id("Email")).sendKeys(email);

        ExtentTestManager.getTest().log(Status.INFO, "Step03");
        driver.findElement(By.id("Password")).sendKeys(password);

        ExtentTestManager.getTest().log(Status.INFO, "Step04");
        driver.findElement(By.cssSelector(".login-button")).click();
        sleepInSecond(2);

        ExtentTestManager.getTest().log(Status.INFO, "Step05");
        Assert.assertTrue(driver.findElement(By.className("ico-account")).isDisplayed());

        ExtentTestManager.getTest().log(Status.INFO, "Step06");
        Assert.assertTrue(driver.findElement(By.className("ico-logout")).isDisplayed());
    }

    public int getRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(999999);
    }

    public void sleepInSecond(long time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Parameters("envName")
    @AfterClass(alwaysRun = true)
    public void afterClass(String envName) {
        closeBrowserAndDriver(envName);
    }
}
