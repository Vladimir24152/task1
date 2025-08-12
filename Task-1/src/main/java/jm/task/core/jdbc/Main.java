package jm.task.core.jdbc;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.PropertiesUtil;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl(choosingAnImplementation());

        userService.createUsersTable();
        userService.saveUser("Name1","Lastname1", (byte) 20);
        userService.saveUser("Name2","Lastname2", (byte) 21);
        userService.saveUser("Name3","Lastname3", (byte) 22);
        userService.saveUser("Name4","Lastname4", (byte) 23);

        userService.getAllUsers().stream().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

    public static UserDao choosingAnImplementation() {
        if (PropertiesUtil.getProperty("dao_realization").equals("jdbc")) {
           return new UserDaoJDBCImpl();
        }else if (PropertiesUtil.getProperty("dao_realization").equals("hibernate")) {
            return new UserDaoHibernateImpl();
        }else {
            throw new IllegalArgumentException("Указана неверная реализация UserDao");
        }
    }
}
