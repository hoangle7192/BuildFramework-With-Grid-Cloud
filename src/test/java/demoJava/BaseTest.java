package demoJava;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

@Slf4j
public abstract class BaseTest {
    @BeforeClass
    public void beforeBaseClass() {
        System.out.println("Parent Before Class method");
    }

    @AfterClass
    public void afterBaseClass() {
        System.out.println("Parent After Class method");
    }

    @BeforeMethod
    public void beforeBaseMethod() {
        System.out.println("Parent Before method");
        try {
            initData();
        } catch (Exception ex) {
            log.error("[setUpClass] Exception:", ex);
        }
    }

    @AfterMethod
    public void afterBaseMethod() {
        System.out.println("Parent After method");
    }

    protected abstract void initData();
}
