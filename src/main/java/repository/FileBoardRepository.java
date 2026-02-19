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

    public FileBoardRepository() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.filePath = Paths.get("db", "post.json");

        try {
            Path parentDir = this.filePath.getParent();
            if (parentDir != null && Files.notExists(parentDir)) {
                Files.createDirectories(parentDir);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getPostList() {
        try {
            File file = this.filePath.toFile();

            List<Post> postList = this.objectMapper.readValue(file, new TypeReference<List<Post>>() {});
            postList = new ArrayList<>(postList);

            return postList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void storePost(Post post) {
        try {
            File file = this.filePath.toFile();

            List<Post> postList;

            Long id = 1L;
            if (file.exists() && file.length() > 0) {
                postList = this.objectMapper.readValue(file, new TypeReference<List<Post>>() {});
                postList = new ArrayList<>(postList);

                for (Post postData : postList) {
                    if (id < postData.getId()) {
                        id = postData.getId();
                    }
                }
                id += 1;
            } else {
                postList = new ArrayList<>();
            }
            post.setId(id);

            postList.add(post);

            this.objectMapper.writeValue(file, postList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Post getPostById(Long id) {
        try {
            File file = this.filePath.toFile();

            List<Post> postList = this.objectMapper.readValue(file, new TypeReference<List<Post>>() {});
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
    public void deletePostById(Long id) {
        try {
            File file = this.filePath.toFile();

            List<Post> postList = this.objectMapper.readValue(file, new TypeReference<List<Post>>() {});
            postList = new ArrayList<>(postList);

            for (int i = 0; i < postList.size(); i++) {
                if (Objects.equals(postList.get(i).getId(), id)) {
                    postList.remove(i);
                    break;
                }
            }

            this.objectMapper.writeValue(file, postList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editPost(Post post) {
        try {
            File file = this.filePath.toFile();

            List<Post> postList = this.objectMapper.readValue(file, new TypeReference<List<Post>>() {});
            postList = new ArrayList<>(postList);

            List<Post> newPostList = new ArrayList<>();

            for (Post postData : postList) {
                if (Objects.equals(postData.getId(), post.getId())) {
                    newPostList.add(post);
                } else {
                    newPostList.add(postData);
                }
            }

            this.objectMapper.writeValue(file, newPostList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
