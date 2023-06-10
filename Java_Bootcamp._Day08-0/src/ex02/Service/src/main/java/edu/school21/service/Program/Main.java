package edu.school21.service.Program;

import edu.school21.service.Repositories.UsersRepository;
import edu.school21.service.Service.UsersServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        System.out.println("/* -------------------------------- JDBCTemplate -------------------------------- */");

        ApplicationContext context =  new AnnotationConfigApplicationContext("edu.school21.service");
        UsersServiceImpl usersService = context.getBean("userService", UsersServiceImpl.class);
        UsersRepository usersRepository = context.getBean("jdbcTemplateRepository", UsersRepository.class);
        usersService.signUp("armenarzz@mail.ru");
        usersService.signUp("arzzezzan@mail.ru");
        System.out.println(usersRepository.findAll());
        System.out.println(usersRepository.findByEmail("armenarzz@mail.ru").get());
    }
}