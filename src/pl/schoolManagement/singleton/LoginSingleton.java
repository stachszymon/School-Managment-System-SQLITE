package pl.schoolManagement.singleton;

import pl.schoolManagement.model.User;

public class LoginSingleton {

    private static LoginSingleton instance = new LoginSingleton();
    private User user = null;

    private LoginSingleton() {

    }

    public static LoginSingleton getInstance() {
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
