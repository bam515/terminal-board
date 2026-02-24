package repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import domain.Post;
import exception.DBConnectionException;

public class JdbcBoardRepository implements BoardRepository {
    private final Properties properties;

    public JdbcBoardRepository() {
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
            throw new DBConnectionException("Failed connect Database.");
        }
    }

    @Override
    public void deletePostById(Long id) {
        String deleteSql = "DELETE FROM post WHERE id = ?";

        try (final Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteSql)) {

            statement.setLong(1, id);

            int result = statement.executeUpdate();
            if (result == 1) {
                System.out.println("Success delete post.");
            } else {
                throw new DBConnectionException("Failed delete post.");
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Can't delete post.");
        }
    }

    @Override
    public void editPost(Post post) {
        String updateSql = "UPDATE post SET title = ?, content = ? WHERE id = ?";

        try (final Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateSql)) {

            statement.setString(1, post.getTitle());
            statement.setString(2, post.getContent());
            statement.setLong(3, post.getId());

            int result = statement.executeUpdate();
            if (result == 1) {
                System.out.println("Success edit post.");
            } else {
                throw new DBConnectionException("Failed edit post.");
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Can't edit post.");
        }
    }

    @Override
    public Post getPostById(Long id) {
        String selectSql = "SELECT * FROM post WHERE id = ?";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectSql)) {

            statement.setLong(1, id);

            Post post = null;
            try (ResultSet resultSet = statement.executeQuery()) {


                while (resultSet.next()) {
                    LocalDateTime createdAt = resultSet.getObject("created_at", LocalDateTime.class);
                    post = new Post(resultSet.getLong("id"), resultSet.getLong("writer_id"), resultSet.getString("title"), resultSet.getString("content"), resultSet.getString("writer"), createdAt);
                }

                if (post == null) {
                    throw new DBConnectionException("Failed find post by id.");
                }
            }

            return post;
        } catch (SQLException e) {
            throw new DBConnectionException("Can't load Post by id.");
        }
    }

    @Override
    public List<Post> getPostList() {
        String selectQuery = "SELECT * FROM post";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            List<Post> postList = new ArrayList<>();
            while (resultSet.next()) {
                LocalDateTime createdAt = resultSet.getObject("created_at", LocalDateTime.class);
                Post post = new Post(resultSet.getLong("id"), resultSet.getLong("writer_id"), resultSet.getString("title"), resultSet.getString("content"), resultSet.getString("writer"), createdAt);
                postList.add(post);
            }

            return postList;
        } catch (SQLException e) {
            throw new DBConnectionException("Can't load Post List.");
        }
    }

    @Override
    public void storePost(Post post) {
        String insertSql = "INSERT INTO post (writer_id, title, content, writer, created_at) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSql)) {

            statement.setLong(1, post.getWriterId());
            statement.setString(2, post.getTitle());
            statement.setString(3, post.getContent());
            statement.setString(4, post.getWriter());
            statement.setObject(5, post.getCreatedAt());

            int result = statement.executeUpdate();
            if (result == 1) {
                System.out.println("Success store post");
            } else {
                throw new DBConnectionException("Failed store post");
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Can't store post.");
        }
    }
}