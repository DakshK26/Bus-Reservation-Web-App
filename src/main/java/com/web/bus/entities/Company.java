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
    private String company;
    private String name;
    private String password;
    private String email;

    /*
    Blank Constructor
     */
    public Company() {
        this.company = null;
        this.name = null;
        this.password = null;
        this.email = null;
    }

    /*
    Default Constructor
     */
    public Company(String company, String name, String password, String email) {
        this.company = company;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    /*
    Getter and Setters
     */

    public String getCompany() {
        return company;
    }

    public void setCompany(String companyname) {
        this.company = company;
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