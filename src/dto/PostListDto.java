package dto;

import java.time.LocalDateTime;

public class PostListDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;

    public PostListDto(Long id, String title, String content, String writer, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
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
