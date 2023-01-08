package com.web.bus.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: GUI for login, Initial user screen
 * Method List: public static void main(String[] args)
 */
@Entity
public class Company {
    @Id
    @GeneratedValue
    /*
    Private Instance Data
     */
    private long id;
    private String name;
    private String password;
    private Bus[] busList;

    /*
    Default Constructor
     */
    public Company(String name, String password) {
        this.name = name;
        this.password = password;
        this.busList = null;
    }

    /*
    Getter and Setters
     */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Bus[] getBusList() {
        return busList;
    }

    public void setBusList(Bus[] busList) {
        this.busList = busList;
    }
}
