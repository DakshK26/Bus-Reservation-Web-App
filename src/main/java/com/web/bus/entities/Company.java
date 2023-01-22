package com.web.bus.entities;

import javax.persistence.*;

/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: GUI for login, Initial user screen
 * Method List: public static void main(String[] args)
 */
//
@Entity
public class Company extends AbstractEntity {

    /*
        Private Instance Data
         */
    private String name;
    private String password;
    private String email;

    /*
    Blank Constructor
     */
    public Company() {
        this.name = null;
        this.password = null;
        this.email = null;
    }

    /*
    Default Constructor
     */
    public Company(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
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