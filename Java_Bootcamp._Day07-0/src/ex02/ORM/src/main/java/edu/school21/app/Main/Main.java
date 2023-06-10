package edu.school21.app.Main;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.app.Model.User;
import edu.school21.app.ORM.OrmManager;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        HikariDataSource dataSource = HikariDataSourceConfig.getDataSource();
        OrmManager manager = new OrmManager(dataSource);
        manager.createTable();

        User user1 = new User(1, "Armen", "Arzumanian", 170);
        User user2 = new User(2, "Vardan", "Arzumanyan", 170);
        User user3 = new User(3, "Temirkan", "Abitov", 180);
        manager.save(user1);
        manager.save(user2);
        manager.save(user3);

        User user1U = new User(1, "Armen", "Arzumanyan", 170);
        manager.update(user1U);

        User userF = manager.findById(1L, User.class);
        System.out.println(userF);

    }
}