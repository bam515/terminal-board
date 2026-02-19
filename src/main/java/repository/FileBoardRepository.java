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

public class FileBoardRepository implements BoardRepository {
    private final ObjectMapper objectMapper;
    private final Path filePath;

    public FileBoardRepository() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.filePath = Paths.get("db", "post.json");
    }

    @Override
    public List<Post> getPostList() {
        return List.of();
    }

    @Override
    public void storePost(Post post) {
        try {
            Path parentDir = this.filePath.getParent();
            if (parentDir != null && Files.notExists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            File file = this.filePath.toFile();
            List<Post> posts;

            Long id = 1L;
            if (file.exists() && file.length() > 0) {
                posts = this.objectMapper.readValue(file, new TypeReference<List<Post>>() {});
                posts = new ArrayList<>(posts);

                id = posts.get(posts.size() - 1).getId() + 1;
            } else {
                posts = new ArrayList<>();
            }
            post.setId(id);

            posts.add(post);

            this.objectMapper.writeValue(file, posts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Post getPostById(Long id) {
        return null;
    }

    @Override
    public void deletePostById(Long id) {

    }
}
