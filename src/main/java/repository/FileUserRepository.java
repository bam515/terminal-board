package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import domain.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileUserRepository implements UserRepository {
    private final ObjectMapper objectMapper;
    private final Path filePath;

    public FileUserRepository() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.filePath = Paths.get("db", "user.json");

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
    public void storeUser(User user) {
        try {
            File file = this.filePath.toFile();
            List<User> userList;

            Long id = 1L;
            if (file.exists() && file.length() > 0) {
                userList = this.objectMapper.readValue(file, new TypeReference<List<User>>() {});
                userList = new ArrayList<>(userList);

                for (User userData : userList) {
                    if (id < userData.getId()) {
                        id = userData.getId();
                    }
                }
                id += 1;
            } else {
                userList = new ArrayList<>();
            }

            user.setId(id);
            userList.add(user);

            this.objectMapper.writeValue(file, userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateLastLoginDate(Long id) {
        try {
            File file = this.filePath.toFile();

            List<User> userList = this.objectMapper.readValue(file, new TypeReference<List<User>>() {});
            userList = new ArrayList<>(userList);

            for (User user : userList) {
                if (Objects.equals(user.getId(), id)) {
                    user.updateLastLoginDate();
                    break;
                }
            }

            this.objectMapper.writeValue(file, userList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getUserList() {
        try {
            File file = this.filePath.toFile();
            List<User> userList = this.objectMapper.readValue(file, new TypeReference<List<User>>() {});
            userList = new ArrayList<>(userList);

            return userList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserById(Long id) {
        try {
            File file = this.filePath.toFile();

            List<User> userList = this.objectMapper.readValue(file, new TypeReference<List<User>>() {});
            userList = new ArrayList<>(userList);

            for (User user : userList) {
                if (Objects.equals(user.getId(), id)) {
                    return user;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getUserName(Long id) {
        try {
            File file = this.filePath.toFile();

            List<User> userList = this.objectMapper.readValue(file, new TypeReference<List<User>>() {});
            userList = new ArrayList<>(userList);

            for (User user : userList) {
                if (Objects.equals(user.getId(), id)) {
                    return user.getName();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void editPassword(User user) {
        try {
            File file = this.filePath.toFile();

            List<User> userList = this.objectMapper.readValue(file, new TypeReference<List<User>>() {});
            userList = new ArrayList<>(userList);

            List<User> newUserList = new ArrayList<>();

            for (User userData : userList) {
                if (Objects.equals(user.getId(), userData.getId())) {
                    newUserList.add(user);
                } else {
                    newUserList.add(userData);
                }
            }

            this.objectMapper.writeValue(file, newUserList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
