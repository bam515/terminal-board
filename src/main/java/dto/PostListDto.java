package dto;

import java.time.LocalDateTime;

public record PostListDto(Long id, String title, String content, String writer, LocalDateTime createdAt) {
}
