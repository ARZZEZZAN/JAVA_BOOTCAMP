package edu.school21.service.Services;

import edu.school21.service.Config.TestApplicationConfig;
import edu.school21.service.Repositories.UsersRepositoryJdbcImpl;
import edu.school21.service.Repositories.UsersRepositoryJdbcTemplateImpl;
import edu.school21.service.Service.UsersService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserServiceImplTest {
    private static UsersService usersService;
    private static UsersService usersServiceTemplate;
    private static UsersRepositoryJdbcImpl usersRepositoryJdbc;
    private static UsersRepositoryJdbcTemplateImpl usersRepositoryJdbcTemplate;

    @BeforeAll
    public static void before() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        usersService = context.getBean("usersServiceImpl", UsersService.class);
        usersServiceTemplate = context.getBean("usersServiceTemplate", UsersService.class);
        usersRepositoryJdbc = context.getBean("usersRepositoryJdbc", UsersRepositoryJdbcImpl.class);
        usersRepositoryJdbcTemplate = context.getBean("usersRepositoryJdbcTemplate", UsersRepositoryJdbcTemplateImpl.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"armenarzz@mail.ru", "arzzezzan@mail.ru", "arzezan@mail.ru", "armyashka2003@mail.ru"})
    public void isSignedUp(String email) {
        usersService.signUp(email);
        System.out.println(usersRepositoryJdbc.findAll());
    }
    @ParameterizedTest
    @ValueSource(strings = {"armenarzz1@mail.ru", "arzzezzan1@mail.ru", "arzezan1@mail.ru", "armyashka12003@mail.ru"})
    public void isSignedUpTemplate(String email) {
        usersServiceTemplate.signUp(email);
        System.out.println(usersRepositoryJdbcTemplate.findAll());
    }
}
