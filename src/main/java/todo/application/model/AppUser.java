package todo.application.model;

import java.util.Objects;
import todo.application.util.Validation;

public class AppUser {
    private String username;
    private String password;
    AppRole role;

    public AppUser(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        int nameMinLength = 3;
        int nameMaxLength = 10;

        if (Validation.isValid("username", username, nameMinLength, nameMaxLength)) {
            this.username = username;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        int passwordMinLength = 8;
        int passwordMaxLength = 30;

        if (Validation.isValid("password", password, passwordMinLength, passwordMaxLength)) {
            this.password = password;
        }
    }

    public AppRole getRole() {
        return role;
    }

    public void setRole(AppRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return username.equals(appUser.username) && role == appUser.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, role);
    }

    @Override
    public String toString() {
        return  "username: " + username + '\n' +
                "password: " + password + '\n' +
                "role: " + role ;
    }
}
