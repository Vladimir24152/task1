package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Driver;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl(new UserDaoJDBCImpl());
        userService.createUsersTable();
        userService.saveUser("Name1","Lastname1", (byte) 20);
        userService.saveUser("Name2","Lastname2", (byte) 21);
        userService.saveUser("Name3","Lastname3", (byte) 22);
        userService.saveUser("Name4","Lastname4", (byte) 23);

        userService.getAllUsers().stream().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
