package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS users (
            id SERIAL PRIMARY KEY,
            name VARCHAR(255),
            lastName VARCHAR(255),
            age INTEGER);
    """;
        Session session = HibernateSessionFactoryUtil.open().openSession();
        Transaction tx1 = session.beginTransaction();
        session.createNativeQuery(sql).executeUpdate();
        tx1.commit();
    }

    @Override
    public void dropUsersTable() {
        Session session = HibernateSessionFactoryUtil.open().openSession();
        Transaction tx1 = session.beginTransaction();
        session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
        tx1.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = HibernateSessionFactoryUtil.open().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(new User(name, lastName, age));
        tx1.commit();
        session.close();
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.open();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(session.get(User.class, id));
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = HibernateSessionFactoryUtil.open().openSession();
        Transaction tx1 = session.beginTransaction();
        List<User> users = session.createQuery("from User",User.class).list();
        tx1.commit();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = HibernateSessionFactoryUtil.open().openSession();
        Transaction tx1 = session.beginTransaction();
        session.createQuery("DELETE FROM User").executeUpdate();
        tx1.commit();
        session.close();
    }
}
