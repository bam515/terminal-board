package repository;

import domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemoryUserRepository implements UserRepository {
    List<User> userList = new ArrayList<>();
    private Long userLastId = 0L;

    @Override
    public void storeUser(User user) {
        this.userLastId += 1;
        user.setId(this.userLastId);

        userList.add(user);
    }

    @Override
    public void updateLastLoginDate(Long id) {
        for (User user : this.userList) {
            if (Objects.equals(user.getId(), id)) {
                user.updateLastLoginDate();
            }
        }
    }

    @Override
    public List<User> getUserList() {
        return new ArrayList<>(this.userList);
    }
}
