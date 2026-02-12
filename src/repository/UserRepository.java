package repository;

import domain.User;

import java.util.List;

public interface UserRepository {

    void storeUser(User user);

    void updateLastLoginDate(Long id);

    List<User> getUserList();
}
