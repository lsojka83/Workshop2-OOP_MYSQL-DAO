package pl.workshop2.w2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {

    private static final String DB_HOSTNAME = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "coderslab";


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getUrl(), DB_USERNAME, DB_PASSWORD);
    }

    private static String getUrl() {
        String database = "workshop2";
        String parameters = "useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
        return String.format("jdbc:mysql://%s:%s/%s?%s", DB_HOSTNAME, DB_PORT, database, parameters);
    }

    private static String getUrlNoDb() {
        String database = "workshop2";
        String parameters = "useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
        return String.format("jdbc:mysql://%s:%s/?%s", DB_HOSTNAME, DB_PORT, parameters);
    }

    public static Connection connectNoDB() throws SQLException {
        return DriverManager.getConnection(getUrlNoDb(), DB_USERNAME, DB_PASSWORD);
    }
}
