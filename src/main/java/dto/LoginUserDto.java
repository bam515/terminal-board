package dto;

import java.time.LocalDateTime;

public record LoginUserDto(Long id, String userId, String name, LocalDateTime createdAt, LocalDateTime lastLoginDate) {
}
