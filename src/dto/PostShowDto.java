package dto;

import java.time.LocalDateTime;

public record PostShowDto(String title, String content, String writer, LocalDateTime createdAt) {
}
