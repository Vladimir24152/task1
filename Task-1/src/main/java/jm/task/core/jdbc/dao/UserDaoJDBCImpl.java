package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.JdbcConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    private void makeARequest(String sql) {
        try (Connection connection = JdbcConnectionUtil.open();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users(
                    id SERIAL PRIMARY KEY,
                    name varchar(255) NOT NULL,
                    lastname varchar(255) NOT NULL,
                    age SMALLINT NOT NULL
                )
        """;
        makeARequest(sql);
    }

    public void dropUsersTable() {
        String sql = """
                DROP TABLE IF EXISTS users;
        """;
        makeARequest(sql);
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = """
            INSERT INTO users(name, lastname, age)
            VALUES (?, ?, ?);
        """;
        try (Connection connection = JdbcConnectionUtil.open();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        String sql = """
                DELETE FROM users WHERE id = ?;
                """;
        try (Connection connection = JdbcConnectionUtil.open();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String sql = """
                SELECT * FROM users;
                """;

        List<User> users = new ArrayList<>();

        try (Connection connection = JdbcConnectionUtil.open();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                User user = new User(resultSet.getString("name"), resultSet.getString("lastname"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = """
                DELETE FROM users;
                """;
        makeARequest(sql);
    }
}
