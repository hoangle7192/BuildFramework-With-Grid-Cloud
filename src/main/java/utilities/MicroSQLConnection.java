package utilities;

import propertiesConfig.ConfigProperties;
import propertiesConfig.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MicroSQLConnection {

    public static Connection getSQLSeverConnection() {
        Environment environment = ConfigProperties.getEnvironment();
        return getSQLSeverConnection(environment.getDBHost(), environment.getDBUser(), environment.getDBPassword());
    }

    private static Connection getSQLSeverConnection(String dbHost, String dbUser, String dbPassword) {
        Connection conn = null;
        try {
            String connectionURL = dbHost + ";encrypt=true;trustServerCertificate=true";
            conn = DriverManager.getConnection(connectionURL, dbUser, dbPassword);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
