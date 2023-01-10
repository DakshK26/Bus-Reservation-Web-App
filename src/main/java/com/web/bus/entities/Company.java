package com.web.bus.entities;

import com.web.bus.services.CompanyRepository;
import com.web.bus.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: GUI for login, Initial user screen
 * Method List: public static void main(String[] args)
 */
//
@Entity
public class Company extends AbstractEntity{
    @Id
    @GeneratedValue
    /*
    Private Instance Data
     */

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
    public Bus[] getBusList() {
        return busList;
    }

    public void setBusList(Bus[] busList) {
        this.busList = busList;
    }
}
