package com.web.bus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: Class to represent bus object
 * Method List:
 */
@Entity
public class Bus {

    @Id
    @GeneratedValue
    /*
    Private Instance Data
     */

    private long id;
    private String startDestination;
    private String endDestination;
    private String numberOfSeats;
    private double startLat;
    private double startLng;
    private double endLat;
    private double endLng;


}
