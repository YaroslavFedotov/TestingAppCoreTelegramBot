package com.accenture.TestingAppCore;

public class User {
    private String login;
    private String name;
    private String password;
    private boolean admin_status;
    public User (String login, String name, String password, boolean admin_status) {
        this.login = login;
        this.name = name;
        this.password = password;
        this.admin_status = admin_status;
    }
    public User() {}
    public String getLogin() {
        return login;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public boolean getAdmin_status() {
        return admin_status;
    }
    public void setLogin(String login) { this.login = login; }
    public void setName(String name) { this.name = name; }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAdmin_status(boolean admin_status) {
        this.admin_status = admin_status;
    }
}
