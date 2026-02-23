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
    private final File dbFile;

    public FileUserRepository() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        this.filePath = Paths.get("db", "user.json");
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
    public void storeUser(User user) {
        try {
            Long id = 0L;
            List<User> userList = this.objectMapper.readValue(this.dbFile, new TypeReference<List<User>>() {});
            userList = new ArrayList<>(userList);

            for (User userData : userList) {
                if (id < userData.getId()) {
                    id = userData.getId();
                }
            }
            id += 1;

            user.setId(id);
            userList.add(user);

            this.objectMapper.writeValue(this.dbFile, userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateLastLoginDate(Long id) {
        try {
            List<User> userList = this.objectMapper.readValue(this.dbFile, new TypeReference<List<User>>() {});
            userList = new ArrayList<>(userList);

            for (User user : userList) {
                if (Objects.equals(user.getId(), id)) {
                    user.updateLastLoginDate();
                    break;
                }
            }

            this.objectMapper.writeValue(this.dbFile, userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();

        try {
            userList = this.objectMapper.readValue(this.dbFile, new TypeReference<List<User>>() {});
            userList = new ArrayList<>(userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public User getUserById(Long id) {
        try {
            List<User> userList = this.objectMapper.readValue(this.dbFile, new TypeReference<List<User>>() {});
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
            List<User> userList = this.objectMapper.readValue(this.dbFile, new TypeReference<List<User>>() {});
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
            List<User> userList = this.objectMapper.readValue(this.dbFile, new TypeReference<List<User>>() {});
            userList = new ArrayList<>(userList);

            List<User> newUserList = new ArrayList<>();

            for (User userData : userList) {
                if (Objects.equals(user.getId(), userData.getId())) {
                    newUserList.add(user);
                } else {
                    newUserList.add(userData);
                }
            }

            this.objectMapper.writeValue(this.dbFile, newUserList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
