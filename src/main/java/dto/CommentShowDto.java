package dto;

import java.time.LocalDateTime;

public record CommentShowDto(Long commentId, String writerName, String content, LocalDateTime createdAt) {
}
