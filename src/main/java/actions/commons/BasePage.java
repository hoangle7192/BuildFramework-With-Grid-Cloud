package actions.commons;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public abstract class BasePage {

    public void sleepInSecond(long timeoutInSecond) {
        try {
            Thread.sleep(timeoutInSecond*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sleepInMiLiSecond(long timeoutInMiLiSecond) {
        try {
            Thread.sleep(timeoutInMiLiSecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* ------------------------------------------------------------------Web Browser------------------------------------------------------------------*/

    public void openUrl(WebDriver driver, String url) {
        driver.get(url);
    }

    protected String getTitle(WebDriver driver) {
        return driver.getTitle();
    }

    protected String getCurrentUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    protected String getPageSource(WebDriver driver) {
        return driver.getPageSource();
    }

    protected void backToPage(WebDriver driver) {
        driver.navigate().back();
    }

    protected void forwardToPage(WebDriver driver) {
        driver.navigate().forward();
    }

    public void refreshPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    protected Alert waitForAlertPresence(WebDriver driver) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, GlobalConstants.LONG_TIME_OUT);
        return webDriverWait.until(ExpectedConditions.alertIsPresent());
    }

    protected void acceptAlert(WebDriver driver) {
        waitForAlertPresence(driver).accept();
    }

    protected void cancelAlert(WebDriver driver) {
        waitForAlertPresence(driver).dismiss();
    }

    protected String getTextOfAlert(WebDriver driver) {
        return waitForAlertPresence(driver).getText();
    }

    protected void sendKeyToAlert(WebDriver driver, String sendKeyValue) {
        waitForAlertPresence(driver).sendKeys(sendKeyValue);
    }

    protected void switchWindowByID(WebDriver driver, String parentWindowID) {
        Set<String> windowIDs = driver.getWindowHandles();
        for (String windowID : windowIDs) {
            if (!windowID.equals(parentWindowID)) {
                driver.switchTo().window(windowID);
            }
        }
    }

    protected void switchWindowByTitle(WebDriver driver, String expectedWindowTitle) {
        Set<String> windowIDs = driver.getWindowHandles();
        for (String windowID : windowIDs) {
            driver.switchTo().window(windowID);
            if (getTitle(driver).equals(expectedWindowTitle)) {
                break;
            }
        }
    }

    protected void closeWindow(WebDriver driver) {
        driver.close();
    }

    protected void closeAllWindowsWithoutParent(WebDriver driver, String parentWindowTitle) {
        Set<String> windowIDs = driver.getWindowHandles();
        for (String windowID : windowIDs) {
            driver.switchTo().window(windowID);
            if (!getTitle(driver).equals(parentWindowTitle)) {
                closeWindow(driver);
            }
        }
    }

    /* ------------------------------------------------------------------Web Element------------------------------------------------------------------*/

    private By getLocator(String locatorType) {
        By by;
        if (locatorType.startsWith("xpath=") || locatorType.startsWith("Xpath=") || locatorType.startsWith("XPATH=")) {
            locatorType = locatorType.substring(6);
            by = By.xpath(locatorType);
        } else if (locatorType.startsWith("css=") || locatorType.startsWith("Css=") || locatorType.startsWith("CSS=")) {
            locatorType = locatorType.substring(4);
            by = By.cssSelector(locatorType);
        } else if (locatorType.startsWith("id=") || locatorType.startsWith("Id=") || locatorType.startsWith("ID=")) {
            locatorType = locatorType.substring(3);
            by = By.id(locatorType);
        } else if (locatorType.startsWith("class=") || locatorType.startsWith("Class=") || locatorType.startsWith("CLASS=")) {
            locatorType = locatorType.substring(6);
            by = By.className(locatorType);
        } else if (locatorType.startsWith("name=") || locatorType.startsWith("Name=") || locatorType.startsWith("NAME=")) {
            locatorType = locatorType.substring(5);
            by = By.name(locatorType);
        } else {
            throw new RuntimeException("Locator Type Is Not Support");
        }
        return by;
    }

    protected WebElement getWebElement(WebDriver driver, String locatorType) {
        return driver.findElement(getLocator(locatorType));
    }

    protected List<WebElement> getListWebElements(WebDriver driver, String locatorType) {
        return driver.findElements(getLocator(locatorType));
    }

    protected String getDynamicXpath(String locatorType, String... dynamicValues) {
        if (locatorType.startsWith("xpath=") || locatorType.startsWith("Xpath=") || locatorType.startsWith("XPATH")) {
            locatorType = String.format(locatorType, (Object[]) dynamicValues);
            return locatorType;
        } else {
            throw new IllegalArgumentException("Locator Type Is Not XPATH");
        }
    }

    protected void clickToElement(WebDriver driver, String locatorType) {
        try {
            getWebElement(driver, locatorType).click();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }
    }

    protected void clickToElement(WebDriver driver, String locatorType, String... dynamicValues) {
        try {
            getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void sendKeyToElement(WebDriver driver, String locatorType, String sendKeyValue) {
        try {
            getWebElement(driver, locatorType).clear();
            getWebElement(driver, locatorType).sendKeys(sendKeyValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void sendKeyToElement(WebDriver driver, String locatorType, String sendKeyValue, String... dynamicValues) {
        try {
            getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).clear();
            getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).sendKeys(sendKeyValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void clearValueInElementByPressKey(WebDriver driver, String locatorType) {
        getWebElement(driver, locatorType).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    }

    protected void clearValueInElementByPressKey(WebDriver driver, String locatorType, String... dynamicValues) {
        getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    }

    protected void selectItemInDefaultDropDown(WebDriver driver, String locatorType, String selectItemName) {
        try {
            new Select(getWebElement(driver, locatorType)).selectByVisibleText(selectItemName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void selectItemInDefaultDropDown(WebDriver driver, String locatorType, String selectItemName, String... dynamicValues) {
        try {
            new Select(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues))).selectByVisibleText(selectItemName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String getSelectedItemInDefaultDropDown(WebDriver driver, String locatorType) {
        return new Select(getWebElement(driver, locatorType)).getFirstSelectedOption().getText();
    }

    protected String getSelectedItemInDefaultDropDown(WebDriver driver, String locatorType, String... dynamicValue) {
        return new Select(getWebElement(driver, getDynamicXpath(locatorType, dynamicValue))).getFirstSelectedOption().getText();
    }

    protected boolean isDropDownMultiple(WebDriver driver, String locatorType) {
        return new Select(getWebElement(driver, locatorType)).isMultiple();
    }

    protected boolean isDropDownMultiple(WebDriver driver, String locatorType, String... dynamicValues) {
        return new Select(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues))).isMultiple();
    }

    protected void selectItemInCustomDropDown(WebDriver driver, String parentLocator, String childLocator, String selectItemName) {
        clickToElement(driver, parentLocator);
        new WebDriverWait(driver, GlobalConstants.LONG_TIME_OUT).until(ExpectedConditions.presenceOfAllElementsLocatedBy(getLocator(childLocator)));
        List<WebElement> itemNames = getListWebElements(driver, childLocator);
        for (WebElement itemName : itemNames) {
            if (itemName.getText().trim().equals(selectItemName)) {
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", itemName);
                sleepInSecond(1);
                itemName.click();
                sleepInSecond(1);
                break;
            }
        }
    }

    protected void selectItemInCustomDropDown(WebDriver driver, String parentLocator, String childLocator, String selectItemName, String... dynamicValues) {
        clickToElement(driver, parentLocator, dynamicValues);
        new WebDriverWait(driver, GlobalConstants.LONG_TIME_OUT).until(ExpectedConditions.presenceOfAllElementsLocatedBy(getLocator(getDynamicXpath(childLocator, dynamicValues))));
        List<WebElement> ItemNames = getListWebElements(driver, getDynamicXpath(childLocator, dynamicValues));
        for (WebElement itemName : ItemNames) {
            if (itemName.getText().trim().equals(selectItemName)) {
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", itemName);
                sleepInSecond(1);
                itemName.click();
                sleepInSecond(1);
                break;
            }
        }
    }

    protected String getElementAttributeValue(WebDriver driver, String locatorType, String attributeName) {
        return getWebElement(driver, locatorType).getAttribute(attributeName);
    }

    protected String getElementAttributeValue(WebDriver driver, String locatorType, String attributeName, String... dynamicValues) {
        return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).getAttribute(attributeName);
    }

    protected String getElementText(WebDriver driver, String locatorType) {
        return getWebElement(driver, locatorType).getText();
    }

    protected String getElementText(WebDriver driver, String locatorType, String... dynamicValues) {
        return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).getText();
    }

    protected String getElementCssValue(WebDriver driver, String locatorType, String propertyName) {
        return getWebElement(driver, locatorType).getCssValue(propertyName);
    }

    protected String getElementCssValue(WebDriver driver, String locatorType, String propertyName, String... dynamicValue) {
        return getWebElement(driver, getDynamicXpath(locatorType, dynamicValue)).getCssValue(propertyName);
    }

    protected String getHeXaColorFromRgba(String rgbaColor) {
        return Color.fromString(rgbaColor).asHex();
    }

    protected int getElementSizes(WebDriver driver, String locatorType) {
        return getListWebElements(driver, locatorType).size();
    }

    protected int getElementSizes(WebDriver driver, String locatorType, String... dynamicValues) {
        return getListWebElements(driver, getDynamicXpath(locatorType, dynamicValues)).size();
    }

    protected boolean isElementSelected(WebDriver driver, String locatorType) {
        return getWebElement(driver, locatorType).isSelected();
    }

    protected boolean isElementSelected(WebDriver driver, String locatorType, String... dynamicValues) {
        return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).isSelected();
    }

    protected boolean isElementDisplayed(WebDriver driver, String locatorType) {
        return getWebElement(driver, locatorType).isDisplayed();
    }

    protected boolean isElementDisplayed(WebDriver driver, String locatorType, String... dynamicValues) {
        return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).isDisplayed();
    }

    protected void overrideImplicitTimeOut(WebDriver driver, long timeOut) {
        driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
    }

    protected boolean isElementUndisplayed(WebDriver driver, String locatorType) {
        overrideImplicitTimeOut(driver, GlobalConstants.SHORT_TIME_OUT);
        List<WebElement> elements = getListWebElements(driver, locatorType);
        overrideImplicitTimeOut(driver, GlobalConstants.LONG_TIME_OUT);
        if (elements.size() == 0) {
            System.out.println("Element Not In Dom");
            return true;
        } else if (!elements.get(0).isDisplayed()) {
            System.out.println("Element In Dom But Invisible");
            return true;
        } else {
            System.out.println("Element In Dom");
            return false;
        }
    }

    protected boolean isElementUndisplayed(WebDriver driver, String locatorType, String... dynamicValues) {
        overrideImplicitTimeOut(driver, GlobalConstants.SHORT_TIME_OUT);
        List<WebElement> elements = getListWebElements(driver, getDynamicXpath(locatorType, dynamicValues));
        overrideImplicitTimeOut(driver, GlobalConstants.LONG_TIME_OUT);
        if (elements.size() == 0) {
            System.out.println("Element Not In Dom");
            return true;
        } else if (!elements.get(0).isDisplayed()) {
            System.out.println("Element In Dom But Invisible");
            return true;
        } else {
            System.out.println("Element In Dom");
            return false;
        }
    }

    protected boolean isElementEnable(WebDriver driver, String locatorType) {
        return getWebElement(driver, locatorType).isEnabled();
    }

    protected boolean isElementEnable(WebDriver driver, String locatorType, String... dynamicValues) {
        return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).isEnabled();
    }

    protected void checkToRadioOrCheckbox(WebDriver driver, String locatorType) {
        if (!isElementSelected(driver, locatorType)) {
            getWebElement(driver, locatorType).click();
        }
    }

    protected void checkToRadioOrCheckbox(WebDriver driver, String locatorType, String... dynamicValues) {
        if (!isElementSelected(driver, locatorType, dynamicValues)) {
            getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).click();
        }
    }

    protected void unCheckToRadioOrCheckbox(WebDriver driver, String locatorType) {
        if (isElementSelected(driver, locatorType)) {
            getWebElement(driver, locatorType).click();
        }
    }

    protected void unCheckToRadioOrCheckbox(WebDriver driver, String locatorType, String... dynamicValues) {
        if (isElementSelected(driver, locatorType, dynamicValues)) {
            getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).click();
        }
    }

    protected void switchToFrame(WebDriver driver, String locatorType) {
        driver.switchTo().frame(getWebElement(driver, locatorType));
    }

    protected void switchToFrame(WebDriver driver, String locatorType, String... dynamicValues) {
        driver.switchTo().frame(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));
    }

    protected void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    protected void clickMouseToElement(WebDriver driver, String locatorType) {
        new Actions(driver).click(getWebElement(driver, locatorType)).perform();
    }

    protected void clickMouseToElement(WebDriver driver, String locatorType, String... dynamicValues) {
        new Actions(driver).click(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues))).perform();
    }

    protected void doubleClickToElement(WebDriver driver, String locatorType) {
        new Actions(driver).doubleClick(getWebElement(driver, locatorType)).perform();
    }

    protected void doubleClickToElement(WebDriver driver, String locatorType, String... dynamicValues) {
        new Actions(driver).doubleClick(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues))).perform();
    }

    protected void hoverMouseToElement(WebDriver driver, String locatorType) {
        new Actions(driver).moveToElement(getWebElement(driver, locatorType)).perform();
    }

    protected void hoverMouseToElement(WebDriver driver, String locatorType, String... dynamicValues) {
        new Actions(driver).moveToElement(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues))).perform();
    }

    protected void rightClickToElement(WebDriver driver, String locatorType) {
        new Actions(driver).contextClick(getWebElement(driver, locatorType)).perform();
    }

    protected void rightClickToElement(WebDriver driver, String locatorType, String dynamicValues) {
        new Actions(driver).contextClick(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues))).perform();
    }

    protected void pressKeyToElement(WebDriver driver, String locatorType, Keys key) {
        new Actions(driver).sendKeys(getWebElement(driver, locatorType), key).perform();
    }

    protected void pressKeyToElement(WebDriver driver, String locatorType, Keys key, String... dynamicValues) {
        new Actions(driver).sendKeys(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)), key).perform();
    }

/*    protected void uploadMultipleFiles(WebDriver driver, String... fileNames) {
        // ĐƯờng dẫn folder upload
        String folderUploadFilePath = GlobalConstants.UPLOAD_FILE_FOLDER;
        // Đường dẫn của tất cả các file
        String fullFileName = null;
        for (String fileName : fileNames) {
            fullFileName = fullFileName + folderUploadFilePath + fileName + "\n";
        }
        fullFileName = fullFileName.trim();
        getWebElement(driver, BasePageUI.UPLOAD_FILE_PATH).sendKeys(fullFileName);
    }*/

    protected void uploadMultipleFiles(WebDriver driver, String... fileNames) {
        String folderUploadFilePath = GlobalConstants.UPLOAD_FILE_FOLDER;
        StringBuilder fileNamePath = new StringBuilder();
        for(String fileName : fileNames) {
            fileNamePath.append(fileNamePath).append(fileName).append("\n");
            //fullFileName = folderUploadFilePath + fileName + "\n";
        }
        StringBuilder fullFileName = new StringBuilder(fileNamePath.toString().trim());
        getWebElement(driver, BasePageUI.UPLOAD_FILE_PATH).sendKeys(fullFileName);
    }

    /*JavaScriptExecutor*/

    protected Object executeForBrowser(WebDriver driver, String javaScript) {
        //jsExecutor = (JavascriptExecutor) driver;
        return ((JavascriptExecutor) driver).executeScript(javaScript);
    }

    protected String getInnerText(WebDriver driver) {
        //jsExecutor = (JavascriptExecutor) driver;
        return (String) ((JavascriptExecutor) driver).executeScript("return document.documentElement.innerText;");
    }

    protected boolean areExpectedTextInInnerText(WebDriver driver, String textExpected) {
        //jsExecutor = (JavascriptExecutor) driver;
        String textActual = (String) ((JavascriptExecutor) driver).executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
        return textActual.equals(textExpected);
    }

    protected void scrollToBottomPage(WebDriver driver) {
        //jsExecutor = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    protected void navigateToUrlByJS(WebDriver driver, String url) {
        //jsExecutor = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("window.location = '" + url + "'");
    }

    protected void highlightElement(WebDriver driver, String locatorType) {
        //jsExecutor = (JavascriptExecutor) driver;
        WebElement element = getWebElement(driver, locatorType);
        String originalStyle = element.getAttribute("style");
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        //jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    protected void clickToElementByJS(WebDriver driver, String locatorType) {
        //jsExecutor = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", getWebElement(driver, locatorType));
    }

    protected void scrollToElement(WebDriver driver, String locatorType) {
        //jsExecutor = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, locatorType));
    }

    protected String getElementValueByJSXpath(WebDriver driver, String xpathLocator) {
        //jsExecutor = (JavascriptExecutor) driver;
        xpathLocator = xpathLocator.replace("xpath=", "");
        return (String) ((JavascriptExecutor) driver).executeScript("return $(document.evaluate(\"" + xpathLocator + "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue).val()");
    }

    protected void sendKeyToElementByJS(WebDriver driver, String locatorType, String sendKeyValue) {
        //jsExecutor = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + sendKeyValue + "')", getWebElement(driver, locatorType));
    }

    protected void removeAttributeInDOM(WebDriver driver, String locatorType, String attributeRemove) {
        //jsExecutor = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getWebElement(driver, locatorType));
    }

    protected boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        //jsExecutor = (JavascriptExecutor) driver;

        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };

        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
            }
        };

        return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
    }

    protected String getElementValidationMessage(WebDriver driver, String locatorType) {
        //jsExecutor = (JavascriptExecutor) driver;
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;", getWebElement(driver, locatorType));
    }

    protected boolean isImageLoaded(WebDriver driver, String locatorType) {
        //jsExecutor = (JavascriptExecutor) driver;
        return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
                getWebElement(driver, locatorType));
    }

    protected boolean isImageLoaded(WebDriver driver, String locatorType, String... dynamicValues) {
        //jsExecutor = (JavascriptExecutor) driver;
        return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
                getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));
    }

    /* ------------------------------------------------------------------Wait------------------------------------------------------------------*/

    protected void waitForElementVisible(WebDriver driver, String locatorType) {
        new WebDriverWait(driver, longTimeout).until(ExpectedConditions.visibilityOfElementLocated(getLocator(locatorType)));
    }

    protected void waitForElementVisible(WebDriver driver, String locatorType, String... dynamicValues) {
        new WebDriverWait(driver, longTimeout).until(ExpectedConditions.visibilityOfElementLocated(getLocator(getDynamicXpath(locatorType, dynamicValues))));
    }

    protected void waitForAllElementVisible(WebDriver driver, String locatorType) {
        new WebDriverWait(driver, longTimeout).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getLocator(locatorType)));
    }

    protected void waitForAllElementVisible(WebDriver driver, String locatorType, String... dynamicValues) {
        new WebDriverWait(driver, longTimeout).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getLocator(getDynamicXpath(locatorType, dynamicValues))));
    }

    protected void waitForElementInvisible(WebDriver driver, String locatorType) {
        new WebDriverWait(driver, longTimeout).until(ExpectedConditions.invisibilityOfElementLocated(getLocator(locatorType)));
    }

    protected void waitForElementInvisible(WebDriver driver, String locatorType, String... dynamicValues) {
        new WebDriverWait(driver, longTimeout).until(ExpectedConditions.invisibilityOfElementLocated(getLocator(getDynamicXpath(locatorType, dynamicValues))));
    }

    protected void waitForAllElementInvisible(WebDriver driver, String locatorType) {
        new WebDriverWait(driver, longTimeout).until(ExpectedConditions.invisibilityOfAllElements(getListWebElements(driver, locatorType)));
    }

    protected void waitForAllElementInvisible(WebDriver driver, String locatorType, String... dynamicValues) {
        new WebDriverWait(driver, longTimeout).until(ExpectedConditions.invisibilityOfAllElements(getListWebElements(driver, getDynamicXpath(locatorType, dynamicValues))));
    }

    protected void waitForElementClickable(WebDriver driver, String locatorType) {
        new WebDriverWait(driver, longTimeout).until(ExpectedConditions.elementToBeClickable(getLocator(locatorType)));
    }

    protected void waitForElementClickable(WebDriver driver, String locatorType, String... dynamicValues) {
        new WebDriverWait(driver, longTimeout).until(ExpectedConditions.elementToBeClickable(getLocator(getDynamicXpath(locatorType, dynamicValues))));
    }

    private long shortTimeout = 5;
    private long longTimeout = 30;

}
