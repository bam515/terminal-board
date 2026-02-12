package service;

import domain.Post;
import dto.PostEditDto;
import dto.PostListDto;
import dto.PostShowDto;
import dto.PostWriteDto;
import exception.FieldEmptyException;
import exception.PostNotFoundException;
import repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;

public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<PostListDto> getPostList() {
        List<PostListDto> postListDtos = new ArrayList<>();
        List<Post> postList = boardRepository.getPostList();

        for (Post post : postList) {
            PostListDto postListDto = new PostListDto(post.getId(), post.getTitle(), post.getContent(), post.getWriter(), post.getCreatedAt());
            postListDtos.add(postListDto);
        }
        return postListDtos;
    }

    public void storePost(PostWriteDto postWriteDto) {
        if (postWriteDto.getTitle().isEmpty()) {
            throw new FieldEmptyException("Title is empty");
        } else if (postWriteDto.getContent().isEmpty()) {
            throw new FieldEmptyException("Content is empty.");
        } else if (postWriteDto.getWriter().isEmpty()) {
            throw new FieldEmptyException("Writer is empty.");
        }

        Post post = new Post(postWriteDto.getTitle(), postWriteDto.getContent(), postWriteDto.getWriter());
        boardRepository.storePost(post);
    }

    public PostShowDto showPost(Long id) {
        Post post = boardRepository.getPostById(id);
        if (post == null) {
            throw new PostNotFoundException("Post is not found.");
        }
        return new PostShowDto(post.getTitle(), post.getContent(), post.getWriter(), post.getCreatedAt());
    }

    public void editPost(PostEditDto postEditDto) {
        if (postEditDto.getTitle().isEmpty()) {
            throw new FieldEmptyException("Title is empty.");
        } else if (postEditDto.getContent().isEmpty()) {
            throw new FieldEmptyException("Content is empty.");
        }

        Long id = postEditDto.getId();
        Post post = boardRepository.getPostById(id);
        if (post == null) {
            throw new PostNotFoundException("Post is not found.");
        }

        post.edit(postEditDto);
    }

    public void deletePost(Long id) {
        Post post = boardRepository.getPostById(id);
        if (post == null) {
            throw new PostNotFoundException("Post is not found.");
        }
        boardRepository.deletePostById(id);
    }
}
