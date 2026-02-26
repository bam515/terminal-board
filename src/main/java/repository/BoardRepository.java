package repository;

import domain.Post;
import java.util.List;

public interface BoardRepository {

    List<Post> getPostList();

    Long storePost(Post post);

    Post getPostById(Long id);

    int deletePostById(Long id);

    int editPost(Post post);
}
