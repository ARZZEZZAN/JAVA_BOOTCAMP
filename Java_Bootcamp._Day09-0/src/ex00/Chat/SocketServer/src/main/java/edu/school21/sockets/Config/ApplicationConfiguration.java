package edu.school21.sockets.Config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
public class ApplicationConfiguration {
    @Value("${db.url}")
    private String URL_DB;
    @Value("${db.user}")
    private String DB_LOGIN;
    @Value("${db.password}")
    private String DB_PASSWORD;

    @Bean
    public DataSource hikariDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(URL_DB);
        hikariConfig.setUsername(DB_LOGIN);
        hikariConfig.setPassword(DB_PASSWORD);
        return new HikariDataSource(hikariConfig);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
