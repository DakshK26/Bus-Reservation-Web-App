package com.web.bus.entities;

import javax.persistence.*;

/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: Class to represent customer
 * Method List:
 */
@Entity
public class Customer extends AbstractEntity{

    /*
    Private Instance Data
     */
    private String username;
    private String name;
    private String password;
    private String email;

    /*
    Default Constructor
     */

    public Customer(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    /*
    Blank Constructor
     */
    public Customer(){
        this.name = null;
        this.email = null;
        this.username = null;
        this.password = null;
    }

    /*
        Getters and Setters
         */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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