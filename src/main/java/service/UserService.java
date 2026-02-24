package service;

import domain.User;
import dto.EditPasswordDto;
import dto.LoginUserDto;
import dto.UserLoginDto;
import dto.UserStoreDto;
import exception.DuplicateUserIdException;
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
        // -- 중복 확인
        this.validateDuplicateUserId(userStoreDto.userId());

        User user = new User(userStoreDto.userId(), userStoreDto.password(), userStoreDto.name(), LocalDateTime.now());
        this.userRepository.storeUser(user);
    }

    public User login(UserLoginDto userLoginDto) {
        List<User> userList = this.userRepository.getUserList();

        for (User user : userList) {
            if (Objects.equals(user.getLoginId(), userLoginDto.loginId())) {
                if (Objects.equals(user.getPassword(), userLoginDto.password())) {
                    this.userRepository.updateLastLoginDate(user.getId());
                    return user;
                } else {
                    throw new WrongPasswordException("Wrong password.");
                }
            }
        }
        throw new UserNotFoundException("User not found.");
    }

    public void editPassword(LoginUserDto loginUserDto, EditPasswordDto editPasswordDto) {
        User user = this.userRepository.getUserById(loginUserDto.id());
        if (Objects.equals(user.getPassword(), editPasswordDto.beforePassword())) {
            user.editPassword(editPasswordDto.newPassword());

            this.userRepository.editPassword(user);
        } else {
            throw new WrongPasswordException("Wrong password.");
        }
    }

    public void validateDuplicateUserId(String userId) {
        List<User> userList = this.userRepository.getUserList();

        for (User user : userList) {
            if (Objects.equals(user.getLoginId(), userId)) {
                throw new DuplicateUserIdException("This user id is already exists.");
            }
        }
    }
}
