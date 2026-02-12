package common;

import domain.User;

public class Session {
    private User user = null;

    public User getUser() {
        return this.user;
    }

    public void login(User user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return this.getUser() != null;
    }

    public void logout() {
        this.user = null;
    }
}
