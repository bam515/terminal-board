package domain;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginDate;

    public User() {}

    public User(String loginId, String password, String name, LocalDateTime createdAt) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.createdAt = createdAt;
    }

    public User(Long id, String loginId, String password, String name, LocalDateTime createdAt, LocalDateTime lastLoginDate) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.createdAt = createdAt;
        this.lastLoginDate = lastLoginDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
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
