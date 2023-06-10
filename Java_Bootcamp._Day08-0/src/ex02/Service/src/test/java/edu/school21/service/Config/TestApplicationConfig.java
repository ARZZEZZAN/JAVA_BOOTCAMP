package edu.school21.service.Config;

import edu.school21.service.Repositories.UsersRepositoryJdbcImpl;
import edu.school21.service.Repositories.UsersRepositoryJdbcTemplateImpl;
import edu.school21.service.Service.UsersService;
import edu.school21.service.Service.UsersServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class TestApplicationConfig {
    @Bean
    public DataSource dataSource() {
        DataSource dataSource = new EmbeddedDatabaseBuilder().
                setType(EmbeddedDatabaseType.HSQL).
                addScript("schema.sql").
                build();
        return dataSource;
    }
    @Bean
    public UsersRepositoryJdbcTemplateImpl usersRepositoryJdbcTemplate() {
        return new UsersRepositoryJdbcTemplateImpl(dataSource());
    }
    @Bean
    public UsersRepositoryJdbcImpl usersRepositoryJdbc() {
        return new UsersRepositoryJdbcImpl(dataSource());
    }
    @Bean
    public UsersService usersServiceImpl() {
        return new UsersServiceImpl(usersRepositoryJdbc());
    }
    @Bean
    public UsersService usersServiceTemplate() {
        return new UsersServiceImpl(usersRepositoryJdbcTemplate());
    }
}
