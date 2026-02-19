package repository;

import domain.Post;
import java.util.List;

public interface BoardRepository {

    List<Post> getPostList();

    void storePost(Post post);

    Post getPostById(Long id);

    void deletePostById(Long id);
}
