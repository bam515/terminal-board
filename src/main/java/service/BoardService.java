package service;

import domain.Post;
import dto.*;
import exception.FieldEmptyException;
import exception.PostNotFoundException;
import exception.UnauthorizedException;
import repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<PostListDto> getPostList() {
        List<PostListDto> postListDtos = new ArrayList<>();
        List<Post> postList = this.boardRepository.getPostList();

        for (Post post : postList) {
            PostListDto postListDto = new PostListDto(post.getId(), post.getTitle(), post.getContent(), post.getWriter(), post.getCreatedAt());
            postListDtos.add(postListDto);
        }
        return postListDtos;
    }

    public void storePost(PostWriteDto postWriteDto) {
        if (postWriteDto.title().isEmpty()) {
            throw new FieldEmptyException("Title is empty");
        } else if (postWriteDto.content().isEmpty()) {
            throw new FieldEmptyException("Content is empty.");
        } else if (postWriteDto.writer().isEmpty()) {
            throw new FieldEmptyException("Writer is empty.");
        }

        Post post = new Post(postWriteDto.writerId(), postWriteDto.title(), postWriteDto.content(), postWriteDto.writer());
        this.boardRepository.storePost(post);
    }

    public PostShowDto showPost(Long id) {
        Post post = this.boardRepository.getPostById(id);
        if (post == null) {
            throw new PostNotFoundException("Post is not found.");
        }
        return new PostShowDto(post.getTitle(), post.getContent(), post.getWriter(), post.getCreatedAt());
    }

    public void editPost(PostEditDto postEditDto) {
        if (postEditDto.title().isEmpty()) {
            throw new FieldEmptyException("Title is empty.");
        } else if (postEditDto.content().isEmpty()) {
            throw new FieldEmptyException("Content is empty.");
        }

        Long id = postEditDto.id();
        Post post = this.boardRepository.getPostById(id);

        post.edit(postEditDto);

        this.boardRepository.editPost(post);
    }

    public void deletePost(Long id) {
        this.boardRepository.deletePostById(id);
    }

    public void validateWriter(LoginUserDto loginUserDto, Long postId) {
        Post post = this.boardRepository.getPostById(postId);
        if (post == null) {
            throw new PostNotFoundException("Post is not found.");
        }

        Long writerId = post.getWriterId();
        if (!Objects.equals(loginUserDto.id(), writerId)) {
            throw new UnauthorizedException("You do not have permission to perform this action.");
        }
    }
}
