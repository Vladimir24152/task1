package jm.task.core.jdbc.util;

public final class RequestConstants {

    private RequestConstants() {}

    public static final String CREATE_USERS_TABLE = """
            CREATE TABLE IF NOT EXISTS users (
            id SERIAL PRIMARY KEY,
            name VARCHAR(255),
            lastName VARCHAR(255),
            age INTEGER);
    """;

    public static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS users";

    public static final String SAVE_USER = "INSERT INTO users(name, lastname, age) VALUES (?, ?, ?);";

    public static final String REMOVE_USER_BY_ID = "DELETE FROM users WHERE id = ?;";

    public static final String GET_ALL_USERS = "SELECT * FROM users;";

    public static final String GET_ALL_USERS_HQL = "from User";

    public static final String CLEAN_USERS_TABLE = "DELETE FROM users;";

    public static final String CLEAN_USERS_TABLE_HQL = "DELETE FROM User";





}
