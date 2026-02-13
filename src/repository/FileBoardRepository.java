package repository;

import domain.Post;

import java.util.List;

public class FileBoardRepository implements BoardRepository {
    @Override
    public List<Post> getPostList() {
        return List.of();
    }

    @Override
    public void storePost(Post post) {

    }

    @Override
    public Post getPostById(Long id) {
        return null;
    }

    @Override
    public void deletePostById(Long id) {

    }
}
