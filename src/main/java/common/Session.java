package common;

import domain.User;
import dto.LoginUserDto;

public class Session {
    private User user = null;

    public LoginUserDto getUser() {
        return new LoginUserDto(this.user.getId(), this.user.getUserId(), this.user.getName(), this.user.getCreatedAt(), this.user.getLastLoginDate());
    }

    public void login(User user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return this.user != null;
    }

    public void logout() {
        this.user = null;
    }
}
