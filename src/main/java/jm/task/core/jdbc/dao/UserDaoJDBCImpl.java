package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {
    }
      private Connection connection;


    public void createUsersTable() {
        connection = getConnection();
        String sqlquery = "CREATE TABLE IF NOT EXISTS USERS (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(45), lastName VARCHAR(45), age TINYINT, PRIMARY KEY (ID))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlquery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        connection = getConnection();
        String sqlquery = "DROP TABLE IF EXISTS USERS";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlquery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        connection = getConnection();
        String sqlquery = "INSERT INTO USERS (NAME, LASTNAME, AGE) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlquery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        connection = getConnection();
        String sqlquery = "DELETE FROM USERS WHERE ID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlquery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        connection = getConnection();
        List<User> listUsers = new ArrayList<>();
        String sqlquery = "SELECT ID, NAME, LASTNAME, AGE FROM USERS";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlquery);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                listUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listUsers;
    }

    public void cleanUsersTable() {
        connection = getConnection();
        String sqlquery = "DELETE FROM USERS";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlquery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
