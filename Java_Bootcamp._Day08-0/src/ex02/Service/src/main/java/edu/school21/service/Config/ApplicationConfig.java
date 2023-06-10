package edu.school21.service.Config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan
@PropertySource("classpath:db.properties")
public class ApplicationConfig {
    @Value("${db.url}")
    private String url;
    @Value("${db.user}")
    private String login;
    @Value("${db.password}")
    private String password;
    @Value("${db.driver.name}")
    private String driver;

    @Bean
    public DriverManagerDataSource driverManagerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, login, password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }
    @Bean
    public HikariDataSource hikariDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(login);
        hikariConfig.setPassword(password);
        return new HikariDataSource(hikariConfig);
    }
}
