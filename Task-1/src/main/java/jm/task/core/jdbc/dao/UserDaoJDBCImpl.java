package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.JdbcConnectionUtil;
import jm.task.core.jdbc.util.RequestConstants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void consumerMethod(Consumer<PreparedStatement> consumer, String sql) {
        try (Connection connection = JdbcConnectionUtil.open();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            consumer.accept(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <R> R functionMethod(Function<PreparedStatement, R> function, String sql) {
        try (Connection connection = JdbcConnectionUtil.open();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            R type = function.apply(statement);
            return type;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createUsersTable() {
        consumerMethod(statement -> {
            try {
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, RequestConstants.CREATE_USERS_TABLE);
    }

    @Override
    public void dropUsersTable() {
        consumerMethod(statement -> {
            try {
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, RequestConstants.DROP_USERS_TABLE);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        consumerMethod(statement -> {
            try {
                statement.setString(1, name);
                statement.setString(2, lastName);
                statement.setByte(3, age);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, RequestConstants.SAVE_USER);

        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {

        consumerMethod(statement -> {
            try {
                statement.setLong(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, RequestConstants.REMOVE_USER_BY_ID);
    }

    @Override
    public List<User> getAllUsers() {
        return functionMethod(statement -> {
            try {
                List<User> users = new ArrayList<>();
                ResultSet resultSet = statement.executeQuery();
                if (resultSet != null) {
                    while (resultSet.next()){
                        User user = new User(resultSet.getString("name"), resultSet.getString("lastname"), resultSet.getByte("age"));
                        user.setId(resultSet.getLong("id"));
                        users.add(user);
                    }
                }
                return users;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, RequestConstants.GET_ALL_USERS);
    }

    @Override
    public void cleanUsersTable() {
        consumerMethod(statement -> {
            try {
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, RequestConstants.CLEAN_USERS_TABLE);    }
}
