package repository;

import domain.User;
import exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemoryUserRepository implements UserRepository {
    private final List<User> userList = new ArrayList<>();
    private Long userLastId = 0L;

    @Override
    public Long storeUser(User user) {
        this.userLastId += 1;
        user.setId(this.userLastId);

        this.userList.add(user);
        return user.getId();
    }

    @Override
    public int updateLastLoginDate(Long id) {
        int result = 0;
        for (User user : this.userList) {
            if (Objects.equals(user.getId(), id)) {
                user.updateLastLoginDate();
                result = 1;
            }
        }
        return result;
    }

    @Override
    public List<User> getUserList() {
        return new ArrayList<>(this.userList);
    }

    @Override
    public User getUserById(Long id) {
        for (User user : this.userList) {
            if (Objects.equals(user.getId(), id)) {
                return user;
            }
        }
        throw new UserNotFoundException("User not found.");
    }

    @Override
    public String getUserName(Long id) {
        User user = this.getUserById(id);
        return user.getName();
    }

    @Override
    public int editPassword(User user) {
        int result = 0;
        return result;
    }
}
