package domain;

import dto.PostEditDto;

import java.time.LocalDateTime;

public class Post {
    private Long id;
    private String title;
    private String content;
    private final String writer;
    private final LocalDateTime createdAt;

    public Post(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void edit(PostEditDto postEditDto) {
        this.title = postEditDto.getTitle();
        this.content = postEditDto.getContent();
    }
}
