package com.web.bus.entities;

import com.web.bus.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private String name;
    private Bus[] listOfBussesBooked;
    private String username;
    private String password;
    @Autowired
    private UserRepository userRepository;

    /*
    Default Constructor
     */

    public Customer(String name, String username, String password) {
        this.name = name;
        this.listOfBussesBooked = null;
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
    public void saveCustomer(Customer customer) {
        userRepository.save(customer);
    }
    /*
    Method to find customer
     */
    public Customer getCustomerByName(String name) {
        return userRepository.findByName(this.name);
    }
    /*
    Check if customer exists
     */
    public boolean checkCustomer(String name) {
        return userRepository.findByName(this.name) != null;
    }

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
