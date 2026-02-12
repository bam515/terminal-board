package repository;

import domain.Post;
import dto.PostListDto;
import dto.PostShowDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BoardRepository {
    List<Post> postList = new ArrayList<>();

    public BoardRepository() {}

    public List<PostListDto> getPostList() {
        List<PostListDto> postListDtoList = new ArrayList<>();
        for (Post post : postList) {
            PostListDto postListDto = new PostListDto(post.getId(), post.getTitle(), post.getContent(), post.getWriter(), post.getCreatedAt());
            postListDtoList.add(postListDto);
        }
        return postListDtoList;
    }

    public void storePost(Post post) {
        Long id = postList.size() + 1L;
        post.setId(id);

        postList.add(post);
    }

    public Post getPostById(Long id) {
        for (Post post : postList) {
            if (Objects.equals(post.getId(), id)) {
                return post;
            }
        }
        return null;
    }

    public void editPost(Post post) {

    }
}
