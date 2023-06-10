package edu.school21.app.Main;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class HikariDataSourceConfig {
    private static HikariDataSource dataSource;
    private static String URL_DB;
    private static String LOGIN_DB;
    private static String PASSWORD_DB;
    static {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/db.properties")) {
            properties.load(fis);
            URL_DB = properties.getProperty("db.url");
            LOGIN_DB = properties.getProperty("db.username");
            PASSWORD_DB = properties.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static HikariDataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL_DB);
        config.setUsername(LOGIN_DB);
        config.setPassword(PASSWORD_DB);
        return new HikariDataSource(config);
    }
}
