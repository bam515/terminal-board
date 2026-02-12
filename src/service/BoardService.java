package service;

import domain.Post;
import dto.PostEditDto;
import dto.PostListDto;
import dto.PostShowDto;
import dto.PostWriteDto;
import repository.BoardRepository;

import java.util.List;

public class BoardService {
    private final BoardRepository boardRepository = new BoardRepository();

    public BoardService() {}

    public List<PostListDto> getPostList() {
        return boardRepository.getPostList();
    }

    public void storePost(PostWriteDto postWriteDto) {
        Post post = new Post(postWriteDto.getTitle(), postWriteDto.getContent(), postWriteDto.getWriter());
        boardRepository.storePost(post);
    }

    public void showPost(Long id) throws Exception {
        Post post = boardRepository.getPostById(id);
        if (post == null) {
            throw new Exception("Not Found Post");
        }
        PostShowDto postShowDto = new PostShowDto(post.getTitle(), post.getContent(), post.getWriter(), post.getCreatedAt());
        System.out.println("title: " + postShowDto.getTitle());
        System.out.println("content: " + postShowDto.getContent());
        System.out.println("writer: " + postShowDto.getWriter());
        System.out.println("createdAt: " + postShowDto.getCreatedAt());
    }

    public void editPost(PostEditDto postEditDto) throws Exception {
        Long id = postEditDto.getId();
        Post post = boardRepository.getPostById(id);
        if (post == null) {
            throw new Exception("Not Found Post");
        }

        post.edit(postEditDto);
    }
}
