package repository;

import domain.Comment;
import exception.CommentNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemoryCommentRepository implements CommentRepository {
    private final List<Comment> commentList = new ArrayList<>();
    private Long commentLastId = 0L;

    public MemoryCommentRepository() {}

    @Override
    public List<Comment> getCommentList(Long postId) {
        List<Comment> commentList1 = new ArrayList<>();
        for (Comment comment : this.commentList) {
            if (Objects.equals(comment.getPostId(), postId)) {
                commentList1.add(comment);
            }
        }
        return commentList1;
    }

    @Override
    public Long writeComment(Comment comment) {
        this.commentLastId += 1;
        comment.setId(this.commentLastId);

        this.commentList.add(comment);

        return comment.getId();
    }

    @Override
    public int deleteComment(Long commentId) {
        int result = 0;
        for (int i = 0; i < this.commentList.size(); i++) {
            if (Objects.equals(this.commentList.get(i).getId(), commentId)) {
                this.commentList.remove(i);
                result = 1;
                break;
            }
        }
        return result;
    }

    @Override
    public Comment getCommentById(Long commentId) {
        for (Comment comment : this.commentList) {
            if (Objects.equals(comment.getId(), commentId)) {
                return comment;
            }
        }
        throw new CommentNotFoundException("Comment Not found.");
    }

    @Override
    public int editComment(Comment comment) {
        return 0;
    }
}
