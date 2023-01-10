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
public class Customer extends AbstractEntity{

    @Id
    @GeneratedValue
    /*
        Private Instance Data
     */
    private Bus[] listOfBussesBooked;
    private String username;
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
        return userRepository.findByName(name);
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
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
