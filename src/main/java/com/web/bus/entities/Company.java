package com.web.bus.entities;

import com.web.bus.services.CompanyRepository;

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
public class Company extends Accounts {
    @Id
    @GeneratedValue
    /*
    Private Instance Data
     */

    private Bus[] busList;
    private String company;

    /*
    Blank Constructor
     */
    public Company(){
        this.name = null;
        this.password = null;
        this.busList = null;
    }

    /*
    Overloaded Constructor
     */
    public Company(String name, String password) {
        this.name = name;
        this.busList = null;
        this.password = password;
    }

    /*

     */

    /*
    Getter and Setters
     */
    public Bus[] getBusList() {
        return busList;
    }

    public void setBusList(Bus[] busList) {
        this.busList = busList;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String companyname) {
        this.company = company;
    }
}
