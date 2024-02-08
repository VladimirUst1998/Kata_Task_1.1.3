package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {
    private SessionFactory sessionFactory;
    private String CREATE_USERS_QUERY = "CREATE TABLE IF NOT EXISTS USERS (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(45), last_name VARCHAR(45), age TINYINT, PRIMARY KEY (ID))";
    private String DROP_USERS_TABLE_QUERY = "DROP TABLE IF EXISTS USERS";
    public UserDaoHibernateImpl() {

    }
    private void sqlQuery(String sql) {
        sessionFactory = getSessionFactory();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            SQLQuery sqlquery = session.createSQLQuery(sql);
            sqlquery.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }


    @Override
    public void createUsersTable() {sqlQuery(CREATE_USERS_QUERY);}

    @Override
    public void dropUsersTable() {sqlQuery(DROP_USERS_TABLE_QUERY);}

    @Override
    public void saveUser(String name, String lastName, byte age) {
        sessionFactory = getSessionFactory();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            User user = new User(name, lastName, age);
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        sessionFactory = getSessionFactory();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        sessionFactory = getSessionFactory();
        List<User> usersList = null;
        try (Session session = sessionFactory.openSession()) {
            usersList = session.createQuery("from User", User.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        sessionFactory = getSessionFactory();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("delete User");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }
}
