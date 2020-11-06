package com.files;

import java.util.Objects;

public class User {
    private String userName;
    private String login;
    private String email;
    private String password;
    private String role;
    private boolean logged = false;

    public User() {
        this.userName = "";
        this.login = "";
        this.email = "";
        this.password = "";
        this.role = "USER";
    }

    public User(String userName, String login, String email, String password, String role, boolean logged) {
        this.userName = userName;
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
        this.logged = logged;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return role.equals(user.role) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(login, user.login) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(logged, user.logged);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, login, email, password, role, logged);
    }

    @Override
    public String toString() {
        return userName + ' ' + login + ' ' + email + ' ' + password + ' ' + role + ' ';
    }
}
