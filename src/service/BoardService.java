package service;

import domain.Post;
import dto.PostEditDto;
import dto.PostListDto;
import dto.PostShowDto;
import dto.PostWriteDto;
import repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;

public class BoardService {
    private final BoardRepository boardRepository = new BoardRepository();

    public BoardService() {}

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
        Post post = new Post(postWriteDto.getTitle(), postWriteDto.getContent(), postWriteDto.getWriter());
        boardRepository.storePost(post);
    }

    public PostShowDto showPost(Long id) throws Exception {
        Post post = boardRepository.getPostById(id);
        if (post == null) {
            throw new Exception("Not Found Post");
        }
        return new PostShowDto(post.getTitle(), post.getContent(), post.getWriter(), post.getCreatedAt());
    }

    public void editPost(PostEditDto postEditDto) throws Exception {
        Long id = postEditDto.getId();
        Post post = boardRepository.getPostById(id);
        if (post == null) {
            throw new Exception("Not Found Post");
        }

        post.edit(postEditDto);
    }

    public void deletePost(Long id) throws Exception {
        Post post = boardRepository.getPostById(id);
        if (post == null) {
            throw new Exception("Not Found Post");
        }
        boardRepository.deletePostById(id);
    }
}
