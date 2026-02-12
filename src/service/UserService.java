package service;

import domain.User;
import dto.UserLoginDto;
import dto.UserStoreDto;
import exception.UserNotFoundException;
import exception.WrongPasswordException;
import repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(UserStoreDto userStoreDto) {
        User user = new User(userStoreDto.getUserId(), userStoreDto.getPassword(), userStoreDto.getName(), LocalDateTime.now());
        this.userRepository.storeUser(user);
    }

    public User login(UserLoginDto userLoginDto) {
        List<User> userList = this.userRepository.getUserList();

        for (User user : userList) {
            if (Objects.equals(user.getUserId(), userLoginDto.getUserId())) {
                if (Objects.equals(user.getPassword(), userLoginDto.getPassword())) {
                    this.userRepository.updateLastLoginDate(user.getId());
                    return user;
                } else {
                    throw new WrongPasswordException("Wrong password.");
                }
            }
        }
        throw new UserNotFoundException("User not found.");
    }
}
