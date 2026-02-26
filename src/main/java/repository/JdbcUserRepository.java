package repository;

import domain.User;
import exception.DBConnectionException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

public class JdbcUserRepository implements UserRepository {
    private final Properties properties;

    public JdbcUserRepository() {
        try (FileInputStream propertiesFile = new FileInputStream("application.properties")) {
            this.properties = new Properties();
            this.properties.load(propertiesFile);
        } catch (FileNotFoundException e) {
            throw new NoSuchElementException("Can't found file.");
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't load file.");
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(this.properties.get("datasource.url").toString(),
                    this.properties.get("datasource.username").toString(),
                    this.properties.get("datasource.password").toString());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DBConnectionException("Failed connect database.");
        }
    }

    @Override
    public Long storeUser(User user) {
        String sql = "INSERT INTO user (login_id, password, name, created_at) VALUES (?, ?, ?, ?)";

        try (Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getLoginId());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setObject(4, user.getCreatedAt());

            int result = statement.executeUpdate();
            if (result == 1) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        return resultSet.getLong(1);
                    } else {
                        throw new DBConnectionException("Failed to retrieve generated ID.");
                    }
                }
            } else {
                throw new DBConnectionException("Failed store user.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DBConnectionException("Failed store user.");
        }
    }

    @Override
    public int updateLastLoginDate(Long id) {
        int result = 0;

        LocalDateTime lastLoginDate = LocalDateTime.now();
        String sql = "UPDATE user SET last_login_date = ? WHERE id = ?";

        try (Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, lastLoginDate);
            statement.setLong(2, id);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DBConnectionException("Failed update last login date.");
        }
        return result;
    }

    @Override
    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM user";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User(resultSet.getLong("id"), resultSet.getString("login_id"),
                        resultSet.getString("password"), resultSet.getString("name"),
                        resultSet.getObject("created_at", LocalDateTime.class), resultSet.getObject("last_login_date", LocalDateTime.class));
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DBConnectionException("Failed load user list.");
        }
        return userList;
    }

    @Override
    public User getUserById(Long id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        User user = null;

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    user = new User(resultSet.getLong("id"), resultSet.getString("login_id"),
                            resultSet.getString("password"), resultSet.getString("name"),
                            resultSet.getObject("created_at", LocalDateTime.class), resultSet.getObject("last_login_date", LocalDateTime.class));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DBConnectionException("Failed load user data.");
        }
        return user;
    }

    @Override
    public String getUserName(Long id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        String name = "";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    name = resultSet.getString("name");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DBConnectionException("Failed load user data.");
        }
        return name;
    }

    @Override
    public int editPassword(User user) {
        int result = 0;
        String sql = "UPDATE user SET password = ? WHERE id = ?";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getPassword());
            statement.setLong(2, user.getId());

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DBConnectionException("Failed edit password.");
        }
        return result;
    }
}
