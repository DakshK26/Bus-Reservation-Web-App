package com.web.bus.entities;

public abstract class Accounts {
    /*
     Instance Data
     */
    String name;
    String password;
    String email;

    public Accounts() {
        this.name = "";
        this.password = "";
        this.email = "";
    }
    /*
    Getter and Setters
     */

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
