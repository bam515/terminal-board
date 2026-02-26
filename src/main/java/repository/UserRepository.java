package repository;

import domain.User;

import java.util.List;

public interface UserRepository {

    Long storeUser(User user);

    int updateLastLoginDate(Long id);

    List<User> getUserList();

    User getUserById(Long id);

    String getUserName(Long id);

    int editPassword(User user);
}
