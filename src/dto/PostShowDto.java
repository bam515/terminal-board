package dto;

import java.time.LocalDateTime;

public class PostShowDto {
    private final String title;
    private final String content;
    private final String writer;
    private final LocalDateTime createdAt;

    public PostShowDto(String title, String content, String writer, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
