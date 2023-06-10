package edu.school21.service.Program;

import edu.school21.service.Models.User;
import edu.school21.service.Repositories.UsersRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        System.out.println("/* ---------------------------------- JDBCApi ---------------------------------- */");

        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        System.out.println(usersRepository.findById(1L));
        System.out.println(usersRepository.findAll());
        usersRepository.save(new User(4L, "armyashka2003@mail.ru"));
        System.out.println(usersRepository.findAll());
        usersRepository.update(new User(4L, "armyashka2002@mail.ru"));
        System.out.println(usersRepository.findAll());
        usersRepository.delete(4L);
        System.out.println(usersRepository.findAll());
        System.out.println(usersRepository.findByEmail("armenarzz@mail.ru").get());

        System.out.println("/* -------------------------------- JDBCTemplate -------------------------------- */");

        usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        System.out.println(usersRepository.findAll());
        System.out.println(usersRepository.findById(1L));
        usersRepository.save(new User(4L, "armyashka2003@mail.ru"));
        System.out.println(usersRepository.findAll());
        usersRepository.update(new User(4L, "armyashka2002@mail.ru"));
        System.out.println(usersRepository.findAll());
        usersRepository.delete(4L);
        System.out.println(usersRepository.findAll());
        System.out.println(usersRepository.findByEmail("armenarzz@mail.ru").get());
    }
}