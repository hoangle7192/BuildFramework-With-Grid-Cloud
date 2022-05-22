package actions.factoryBrowser;

import actions.commons.GlobalConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;


public class EdgeDriverManager implements BrowserFactory {

    @Override
    public WebDriver getBrowserDriver() {

        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.setCapability("disable-infobars", true);
        options.setCapability("extensionPaths", GlobalConstants.ADBLOCK_EXTENSION_OF_CHROME);

        return new EdgeDriver(options);
    }

}
