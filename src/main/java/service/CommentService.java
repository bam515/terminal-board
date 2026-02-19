package service;

import domain.Comment;
import dto.CommentEditDto;
import dto.CommentShowDto;
import dto.CommentWriteDto;
import dto.LoginUserDto;
import exception.FieldEmptyException;
import exception.UnauthorizedException;
import repository.CommentRepository;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public List<CommentShowDto> getCommentList(Long postId) {
        List<CommentShowDto> commentShowDtoList = new ArrayList<>();
        List<Comment> commentList = this.commentRepository.getCommentList(postId);

        for (Comment comment : commentList) {
            String writerName = this.userRepository.getUserName(comment.getUserId());
            CommentShowDto commentShowDto = new CommentShowDto(comment.getId(), writerName, comment.getContent(), comment.getCreatedAt());
            commentShowDtoList.add(commentShowDto);
        }
        return commentShowDtoList;
    }

    public void writeComment(CommentWriteDto commentWriteDto, LoginUserDto loginUserDto) {
        this.validateContent(commentWriteDto.content());

        Comment comment = new Comment(loginUserDto.id(), commentWriteDto.postId(), commentWriteDto.content());
        this.commentRepository.writeComment(comment);
    }

    public void editComment(CommentEditDto commentEditDto, LoginUserDto loginUserDto) {
        this.validateComment(commentEditDto.commentId(), loginUserDto);

        this.validateContent(commentEditDto.content());

        Comment comment = this.commentRepository.getCommentById(commentEditDto.commentId());
        comment.editComment(commentEditDto.content());
    }

    public void deleteComment(Long commentId, LoginUserDto loginUserDto) {
        this.validateComment(commentId, loginUserDto);

        this.commentRepository.deleteComment(commentId);
    }

    public void validateComment(Long commentId, LoginUserDto loginUserDto) {
        Comment comment = this.commentRepository.getCommentById(commentId);
        if (!Objects.equals(comment.getUserId(), loginUserDto.id())) {
            throw new UnauthorizedException("You do not have permission to perform this action.");
        }
    }

    public void validateContent(String content) {
        if (content.isEmpty()) {
            throw new FieldEmptyException("Comment is empty.");
        }
    }
}
