package propertiesConfig;

import org.aeonbits.owner.Config;
import org.testng.annotations.Parameters;

@Config.Sources("classpath:${severName}.properties")
public interface Environment extends Config {

    @Key("urlAdmin")
    String getUrlAdmin();

    @Key("urlUser")
    String getUrlUser();

    @Key("db.host")
    String getDBHost();

    @Key("db.user")
    String getDBUser();

    @Key("db.password")
    String getDBPassword();
}
