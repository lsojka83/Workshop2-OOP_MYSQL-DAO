package pl.workshop2.w2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDb {

    public static void createDatabase() {
        try (Connection connection = DbUtil.connectNoDB();
             Statement statement = connection.createStatement();) {
            String sql = "CREATE DATABASE IF NOT EXISTS workshop2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;";
            statement.executeUpdate(sql);
            System.out.println("Database created successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTable() {

        String sql = "CREATE TABLE IF NOT EXISTS users(" +
                "id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "email VARCHAR(255) NOT NULL UNIQUE, " +
                "username VARCHAR(255) NOT NULL, " +
                "password VARCHAR(60) NOT NULL" +
                ");";

        try (Connection connection = DbUtil.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
