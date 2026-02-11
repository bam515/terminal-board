package dto;

import java.time.LocalDateTime;

public class PostShowDto {
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;

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
