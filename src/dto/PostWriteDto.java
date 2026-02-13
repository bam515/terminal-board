package dto;

public class PostWriteDto {
    private final Long writerId;
    private final String title;
    private final String content;
    private final String writer;

    public PostWriteDto(Long writerId, String title, String content, String writer) {
        this.writerId = writerId;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public Long getWriterId() {
        return writerId;
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
}
