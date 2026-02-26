package repository;

import domain.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> getCommentList(Long postId);

    Long writeComment(Comment comment);

    int deleteComment(Long commentId);

    Comment getCommentById(Long commentId);

    int editComment(Comment comment);
}
