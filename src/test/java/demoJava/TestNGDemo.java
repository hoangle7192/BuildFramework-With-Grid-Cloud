package demoJava;

import org.testng.annotations.*;

public class TestNGDemo extends BaseTest{
    @BeforeClass
    public void beforeChildClass() {
        System.out.println("Child Before Class method");
    }

    @AfterClass
    public void afterChildClass() {
        System.out.println("Child After Class method");
    }

    @BeforeMethod
    public void beforeChildMethod() {
        System.out.println("Child Before method");
    }

    @AfterMethod
    public void afterChildMethod() {
        System.out.println("Child After method");
    }

    @Test
    public void testMethod01() {
        System.out.println("Test method 01 under TestClass");
    }


    @Test
    public void testMethod02() {
        System.out.println("Test method 02 under TestClass");
    }

    @Test
    public void testMethod03() {
        System.out.println("Test method 03 under TestClass");
    }

    @Override
    protected void initData() {
        System.out.println("day la init");
    }


}
