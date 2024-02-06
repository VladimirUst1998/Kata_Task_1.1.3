package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // Реализация настройки соединения с БД
    private static final String USERNAME = "Ust_11";
    private static final String PASSWORD = "Root_1124";
    private static final String URL = "jdbc:mysql://localhost:3306/task_1.1.2";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Configuration getConfiguration() {
        Configuration configuration = new Configuration();

        configuration.setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        configuration.setProperty(Environment.URL, URL);
        configuration.setProperty(Environment.USER, USERNAME);
        configuration.setProperty(Environment.PASS, PASSWORD);
        configuration.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty(Environment.SHOW_SQL, "false");
        configuration.setProperty(Environment.FORMAT_SQL, "true");



        return configuration;
    }

    public SessionFactory getSessionFactory() {
        Configuration configuration = getConfiguration();
        configuration.addAnnotatedClass(User.class);
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

}
