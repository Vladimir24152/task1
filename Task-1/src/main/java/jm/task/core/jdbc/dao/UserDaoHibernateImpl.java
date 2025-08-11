package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateSessionFactoryUtil;
import jm.task.core.jdbc.util.RequestConstants;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoHibernateImpl.class.getName());

    public UserDaoHibernateImpl() {

    }

    private void consumerMethod(Consumer<Session> consumer) {
        try (Session session = HibernateSessionFactoryUtil.open().openSession()){
            Transaction tx1 = session.beginTransaction();
            consumer.accept(session);
            tx1.commit();
        }
    }
    private  <R> R functionMethod(Function<Session, R> function) {
        try (Session session = HibernateSessionFactoryUtil.open().openSession()) {
            Transaction tx1 = session.beginTransaction();
            R type = function.apply(session);
            tx1.commit();
            return type;
        }
    }

    @Override
    public void createUsersTable() {
        consumerMethod((session) -> session.createNativeQuery(RequestConstants.CREATE_USERS_TABLE).executeUpdate());
    }

    @Override
    public void dropUsersTable() {
        consumerMethod((session) -> session.createNativeQuery(RequestConstants.DROP_USERS_TABLE).executeUpdate());
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        consumerMethod((session) -> session.save(new User(name, lastName, age)));
        LOGGER.info("User с именем – " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        consumerMethod((session) -> session.delete(session.get(User.class, id)));
    }

    @Override
    public List<User> getAllUsers() {
        return functionMethod(session -> session.createQuery(RequestConstants.GET_ALL_USERS_HQL,User.class).list());
    }

    @Override
    public void cleanUsersTable() {
        consumerMethod((session) -> session.createQuery(RequestConstants.CLEAN_USERS_TABLE_HQL).executeUpdate());
    }
}
