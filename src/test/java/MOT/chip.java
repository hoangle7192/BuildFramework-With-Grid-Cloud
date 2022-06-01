package MOT;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class chip {
    static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://login.mailchimp.com/signup/");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        System.out.println("Ok1");
        driver.findElement(By.cssSelector("#email")).sendKeys("automation@gmail.com");
        /*driver.findElement(By.cssSelector("#new_username")).click();
        String value = driver.findElement(By.cssSelector("#new_username")).getAttribute("value");
        System.out.println("value: " + value);*/

        //Thread.sleep(5000);
        driver.findElement(By.cssSelector("#new_username")).clear();//sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        //Thread.sleep(5000);
        driver.findElement(By.cssSelector("#new_username")).sendKeys("automation");
        Thread.sleep(5000);
        System.out.println("Ok2");
        driver.quit();
    }
}
