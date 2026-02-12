package dto;

import java.time.LocalDateTime;

public class LoginUserDto {
    private final Long id;
    private final String userId;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastLoginDate;

    public LoginUserDto(Long id, String userId, String name, LocalDateTime createdAt, LocalDateTime lastLoginDate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.createdAt = createdAt;
        this.lastLoginDate = lastLoginDate;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
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
}
