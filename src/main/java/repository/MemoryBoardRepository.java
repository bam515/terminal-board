package repository;

import domain.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemoryBoardRepository implements BoardRepository {
    private final List<Post> postList = new ArrayList<>();
    private Long postLastId = 0L;

    public MemoryBoardRepository() {}

    @Override
    public List<Post> getPostList() {
        return new ArrayList<>(this.postList);
    }

    @Override
    public void storePost(Post post) {
        this.postLastId += 1;
        post.setId(this.postLastId);

        this.postList.add(post);
    }

    @Override
    public Post getPostById(Long id) {
        for (Post post : this.postList) {
            if (Objects.equals(post.getId(), id)) {
                return post;
            }
        }
        return null;
    }

    @Override
    public void deletePostById(Long id) {
        for (int i = 0; i < this.postList.size(); i++) {
            if (Objects.equals(this.postList.get(i).getId(), id)) {
                this.postList.remove(i);
                break;
            }
        }
    }
}
