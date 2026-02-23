package repository;

import domain.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> getCommentList(Long postId);

    void writeComment(Comment comment);

    void deleteComment(Long commentId);

    Comment getCommentById(Long commentId);

    void editComment(Comment comment);
}
