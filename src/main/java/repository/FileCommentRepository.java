package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import domain.Comment;
import exception.CommentNotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileCommentRepository implements CommentRepository {
    private final ObjectMapper objectMapper;
    private final Path filePath;
    private final File dbFile;

    public FileCommentRepository() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        this.filePath = Paths.get("db", "comment.json");
        this.dbFile = this.filePath.toFile();

        try {
            Path parentDir = this.filePath.getParent();
            if (parentDir != null && Files.notExists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            if (!this.dbFile.exists()) {
                Files.createFile(this.filePath);
                Files.writeString(this.filePath, "[]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteComment(Long commentId) {
        try {
            List<Comment> commentList = this.objectMapper.readValue(this.dbFile, new TypeReference<List<Comment>>() {});
            commentList = new ArrayList<>(commentList);

            for (int i = 0; i < commentList.size(); i++) {
                if (Objects.equals(commentId, commentList.get(i).getId())) {
                    commentList.remove(i);
                    break;
                }
            }

            this.objectMapper.writeValue(this.dbFile, commentList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }

    @Override
    public Comment getCommentById(Long commentId) {
        try {
            List<Comment> commentList = this.objectMapper.readValue(this.dbFile, new TypeReference<List<Comment>>() {});
            commentList = new ArrayList<>(commentList);
            
            for (Comment comment : commentList) {
                if (Objects.equals(comment.getId(), commentId)) {
                    return comment;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new CommentNotFoundException("Comment is not founded.");
    }

    @Override
    public List<Comment> getCommentList(Long postId) {
        List<Comment> data = new ArrayList<>();

        try {
            List<Comment> commentList = this.objectMapper.readValue(this.dbFile, new TypeReference<List<Comment>>() {});
            commentList = new ArrayList<>(commentList);
                
            for (Comment comment : commentList) {
                if (Objects.equals(comment.getPostId(), postId)) {
                    data.add(comment);
                }
            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public void writeComment(Comment comment) {
        try {
            List<Comment> commentList;

            Long id = 0L;
            commentList = this.objectMapper.readValue(this.dbFile, new TypeReference<List<Comment>>() {});
            commentList = new ArrayList<>(commentList);

            for (Comment commentData : commentList) {
                if (id < commentData.getId()) {
                    id = commentData.getId();
                }
            }
            id += 1;

            comment.setId(id);

            commentList.add(comment);

            this.objectMapper.writeValue(this.dbFile, commentList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editComment(Comment comment) {
        try {
            List<Comment> commentList = this.objectMapper.readValue(this.dbFile, new TypeReference<List<Comment>>() {});
            commentList = new ArrayList<>(commentList);

            List<Comment> newCommentList = new ArrayList<>();

            for (Comment commentData : commentList) {
                if (Objects.equals(comment.getId(), commentData.getId())) {
                    newCommentList.add(comment);
                } else {
                    newCommentList.add(commentData);
                }
            }

            this.objectMapper.writeValue(this.dbFile, newCommentList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}