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
//
@Entity
public class Company extends Accounts {
    /*
    Private Instance Data
     */
    @Id
    @GeneratedValue
    private Long id;
    private String company;

    /*
    Blank Constructor
     */
    public Company(){
        this.name = null;
        this.password = null;
    }

    /*
    Overloaded Constructor
     */
    public Company(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /*

     */

    /*
    Getter and Setters
     */

    public String getCompany() {
        return company;
    }

    public void setCompany(String companyname) {
        this.company = company;
    }
}
