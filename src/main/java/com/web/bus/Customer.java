package com.web.bus;

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
public class Customer {

    @Id
    @GeneratedValue
    /*
        Private Instance Data
     */
    private Long id;
    private String seatNumber;
    private String name;

    private Bus[] listOfBussesBooked;
    private String username;
    private String password;

    /*
    Getters and Setters
     */
    public Bus[] getListOfBussesBooked() {
        return listOfBussesBooked;
    }

    public void setListOfBussesBooked(Bus[] listOfBussesBooked) {
        this.listOfBussesBooked = listOfBussesBooked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
