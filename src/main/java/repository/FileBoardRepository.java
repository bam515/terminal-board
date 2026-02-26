package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import domain.Post;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileBoardRepository implements BoardRepository {
    private final ObjectMapper objectMapper;
    private final Path filePath;
    private final File dbFile;

    public FileBoardRepository() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        this.filePath = Paths.get("db", "post.json");
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
    public List<Post> getPostList() {
        List<Post> postList = new ArrayList<>();

        try {
            postList = this.objectMapper.readValue(this.dbFile, new TypeReference<List<Post>>() {});
            postList = new ArrayList<>(postList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return postList;
    }

    @Override
    public Long storePost(Post post) {
        Long result = 0L;
        try {
            List<Post> postList;

            Long id = 0L;
            postList = this.objectMapper.readValue(this.dbFile, new TypeReference<List<Post>>() {});
            postList = new ArrayList<>(postList);

            for (Post postData : postList) {
                if (id < postData.getId()) {
                    id = postData.getId();
                }
            }
            id += 1;
            post.setId(id);

            postList.add(post);

            this.objectMapper.writeValue(this.dbFile, postList);

            result = post.getId();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Post getPostById(Long id) {
        try {
            List<Post> postList = this.objectMapper.readValue(this.dbFile, new TypeReference<List<Post>>() {});
            postList = new ArrayList<>(postList);

            for (Post post : postList) {
                if (Objects.equals(post.getId(), id)) {
                    return post;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int deletePostById(Long id) {
        int result = 0;
        try {
            List<Post> postList = this.objectMapper.readValue(this.dbFile, new TypeReference<List<Post>>() {});
            postList = new ArrayList<>(postList);

            for (int i = 0; i < postList.size(); i++) {
                if (Objects.equals(postList.get(i).getId(), id)) {
                    postList.remove(i);
                    result = 1;
                    break;
                }
            }

            this.objectMapper.writeValue(this.dbFile, postList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int editPost(Post post) {
        try {
            List<Post> postList = this.objectMapper.readValue(this.dbFile, new TypeReference<List<Post>>() {});
            postList = new ArrayList<>(postList);

            List<Post> newPostList = new ArrayList<>();

            for (Post postData : postList) {
                if (Objects.equals(postData.getId(), post.getId())) {
                    newPostList.add(post);
                } else {
                    newPostList.add(postData);
                }
            }

            this.objectMapper.writeValue(this.dbFile, newPostList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
