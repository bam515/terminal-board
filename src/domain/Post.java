package domain;

import dto.PostEditDto;

import java.time.LocalDateTime;

public class Post {
    private Long id;
    private final Long writerId;
    private String title;
    private String content;
    private final String writer;
    private final LocalDateTime createdAt;

    public Post(Long writerId, String title, String content, String writer) {
        this.writerId = writerId;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdAt = LocalDateTime.now();
    }

    public Long getWriterId() {
        return this.writerId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getWriter() {
        return this.writer;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void edit(PostEditDto postEditDto) {
        this.title = postEditDto.title();
        this.content = postEditDto.content();
    }
}
