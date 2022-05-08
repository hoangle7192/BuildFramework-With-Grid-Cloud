package propertiesConfig;

import org.aeonbits.owner.ConfigFactory;

public class ConfigProperties {

    public ConfigProperties() {
        ConfigFactory.setProperty("severName", System.getProperty("severName"));
    }

    public static Environment getEnvironment() {
        return ConfigFactory.create(Environment.class);
    }

}
