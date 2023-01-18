package com.web.bus.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: Class to represent customer
 * Method List:
 */
@Entity
public class Customer extends Accounts{
    /*
        Private Instance Data
     */
    @Id
    @GeneratedValue
    private Long id;
    private Bus[] listOfBussesBooked;
    private String username;

    /*
    Default Constructor
     */

    public Customer(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.listOfBussesBooked = new Bus[0];
        this.username = username;
        this.password = password;
    }

    /*
    Blank Constructor
     */
    public Customer(){
        this.name = null;
        this.listOfBussesBooked = null;
        this.username = null;
        this.password = null;
    }

    /*
    Method to save customer
     */



    /*
        Getters and Setters
         */
    public Bus[] getListOfBussesBooked() {
        return listOfBussesBooked;
    }

    public void setListOfBussesBooked(Bus[] listOfBussesBooked) {
        this.listOfBussesBooked = listOfBussesBooked;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /*
    To string method
     */
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password;
    }

}
