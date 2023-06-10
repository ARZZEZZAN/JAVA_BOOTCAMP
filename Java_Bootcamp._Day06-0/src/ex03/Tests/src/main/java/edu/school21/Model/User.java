package edu.school21.Model;

import java.util.Objects;

public class User {
    private Long id;
    private String Login;
    private String password;
    private boolean authentication;

    public User(Long id, String login, String password, boolean authentication) {
        this.id = id;
        Login = login;
        this.password = password;
        this.authentication = authentication;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return authentication == user.authentication && Objects.equals(id, user.id) && Objects.equals(Login, user.Login) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Login, password, authentication);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", Login='" + Login + '\'' +
                ", password='" + password + '\'' +
                ", authentication=" + authentication +
                '}';
    }
}
