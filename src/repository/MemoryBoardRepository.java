package repository;

import domain.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemoryBoardRepository implements BoardRepository {
    List<Post> postList = new ArrayList<>();
    private Long postLastId = 0L;

    public MemoryBoardRepository() {}

    @Override
    public List<Post> getPostList() {
        return new ArrayList<>(postList);
    }

    @Override
    public void storePost(Post post) {
        postLastId += 1;
        post.setId(postLastId);

        postList.add(post);
    }

    @Override
    public Post getPostById(Long id) {
        for (Post post : postList) {
            if (Objects.equals(post.getId(), id)) {
                return post;
            }
        }
        return null;
    }

    @Override
    public void deletePostById(Long id) {
        for (int i = 0; i < postList.size(); i++) {
            if (Objects.equals(postList.get(i).getId(), id)) {
                postList.remove(i);
                break;
            }
        }
    }
}
