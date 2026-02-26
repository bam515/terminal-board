package repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import domain.Post;
import exception.DBConnectionException;
import exception.PostNotFoundException;

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
    public int deletePostById(Long id) {
        String deleteSql = "DELETE FROM post WHERE id = ?";
        int result = 0;

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteSql)) {

            statement.setLong(1, id);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBConnectionException("Can't delete post.");
        }
        return result;
    }

    @Override
    public int editPost(Post post) {
        int result = 0;
        String updateSql = "UPDATE post SET title = ?, content = ? WHERE id = ?";

        try (final Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateSql)) {

            statement.setString(1, post.getTitle());
            statement.setString(2, post.getContent());
            statement.setLong(3, post.getId());

            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBConnectionException("Can't edit post.");
        }
        return result;
    }

    @Override
    public Post getPostById(Long id) {
        Post post = null;
        String selectSql = "SELECT * FROM post WHERE id = ?";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectSql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    LocalDateTime createdAt = resultSet.getObject("created_at", LocalDateTime.class);
                    post = new Post(resultSet.getLong("id"), resultSet.getLong("writer_id"), resultSet.getString("title"), resultSet.getString("content"), resultSet.getString("writer"), createdAt);
                } else {
                    throw new PostNotFoundException("Post not found.");
                }
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Can't load Post by id.");
        }
        return post;
    }

    @Override
    public List<Post> getPostList() {
        List<Post> postList = new ArrayList<>();
        String selectQuery = "SELECT * FROM post";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                LocalDateTime createdAt = resultSet.getObject("created_at", LocalDateTime.class);
                Post post = new Post(resultSet.getLong("id"), resultSet.getLong("writer_id"), resultSet.getString("title"),
                        resultSet.getString("content"), resultSet.getString("writer"), createdAt);
                postList.add(post);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Can't load Post List.");
        }
        return postList;
    }

    @Override
    public Long storePost(Post post) {
        String insertSql = "INSERT INTO post (writer_id, title, content, writer, created_at) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, post.getWriterId());
            statement.setString(2, post.getTitle());
            statement.setString(3, post.getContent());
            statement.setString(4, post.getWriter());
            statement.setObject(5, post.getCreatedAt());

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
                throw new DBConnectionException("Failed store post");
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Can't store post.");
        }
    }
}