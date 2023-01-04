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
    private double distance;

    /*
    Method to calculate distance between start and end
     */
    // add a method to calculate the distance between the start and end destinations
    public void calculateDistance() {
        final int EARTH_RADIUS = 6371; // radius of the Earth in kilometers

        // Convert Distance to Radians
        double startLatRadians = Math.toRadians(startLat);
        double endLatRadians = Math.toRadians(endLat);
        double latDeltaRadians = Math.toRadians(endLat - startLat);
        double lngDeltaRadians = Math.toRadians(endLng - startLng);

        // Use Haversine Formula
        double a = Math.sin(latDeltaRadians / 2) * Math.sin(latDeltaRadians / 2) +
                Math.cos(startLatRadians) * Math.cos(endLatRadians) *
                        Math.sin(lngDeltaRadians / 2) * Math.sin(lngDeltaRadians / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Set distance
        this.distance = EARTH_RADIUS * c;
    }
}



