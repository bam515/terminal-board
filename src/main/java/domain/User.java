package domain;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String userId;
    private String password;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginDate;

    public User() {}

    public User(String userId, String password, String name, LocalDateTime createdAt) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.createdAt = createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void editPassword(String password) {
        this.password = password;
    }

    public void updateLastLoginDate() {
        this.lastLoginDate = LocalDateTime.now();
    }
}
