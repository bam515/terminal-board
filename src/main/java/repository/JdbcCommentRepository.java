package repository;

import domain.Comment;
import exception.CommentNotFoundException;
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

public class JdbcCommentRepository implements CommentRepository {
    private final Properties properties;

    public JdbcCommentRepository() {
        try (FileInputStream propertiesFile = new FileInputStream("application.properties")) {
            this.properties = new Properties();
            this.properties.load(propertiesFile);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            throw new NoSuchElementException("Can't found file.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
    public List<Comment> getCommentList(Long postId) {
        List<Comment> commentList = new ArrayList<>();
        String sql = "SELECT * FROM comment WHERE post_id = ?";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, postId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    commentList.add(new Comment(resultSet.getLong("id"), resultSet.getLong("user_id"),
                            resultSet.getLong("post_id"), resultSet.getString("content"),
                            resultSet.getObject("created_at", LocalDateTime.class)));

                }
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Failed load comment list.");
        }
        return commentList;
    }

    @Override
    public Long writeComment(Comment comment) {
        String sql = "INSERT INTO comment (user_id, post_id, content, created_at) VALUES (?, ?, ?, ?)";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, comment.getUserId());
            statement.setLong(2, comment.getPostId());
            statement.setString(3, comment.getContent());
            statement.setObject(4, LocalDateTime.now());

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
                throw new DBConnectionException("Failed store comment.");
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Failed store comment.");
        }
    }

    @Override
    public int deleteComment(Long commentId) {
        int result = 0;
        String sql = "DELETE FROM comment WHERE id = ?";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, commentId);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBConnectionException("Failed delete comment.");
        }
        return result;
    }

    @Override
    public Comment getCommentById(Long commentId) {
        String sql = "SELECT * FROM comment WHERE id = ?";
        Comment comment = null;

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, commentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    comment = new Comment(resultSet.getLong("id"), resultSet.getLong("user_id"),
                            resultSet.getLong("post_id"), resultSet.getString("content"),
                            resultSet.getObject("created_at", LocalDateTime.class));
                } else {
                    throw new CommentNotFoundException("Comment not found.");
                }
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Failed load comment data.");
        }
        return comment;
    }

    @Override
    public int editComment(Comment comment) {
        int result = 0;
        String sql = "UPDATE comment SET content = ? WHERE id = ?";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, comment.getContent());
            statement.setLong(2, comment.getId());

            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBConnectionException("Failed edit comment.");
        }
        return result;
    }
}
