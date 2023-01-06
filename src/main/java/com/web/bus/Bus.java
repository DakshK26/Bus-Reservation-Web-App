package com.web.bus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

    /*
    Method to find longtitude and langtitude from adress
     */
    public double[] getCoordinates(String location) throws IOException {
        String API_KEY = "AIzaSyC6tR-p77Y-NHQyWv7JnRsFlfhOhZvkhTI";
        String requestUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                location + "&key=" + API_KEY;

        // make the HTTP GET request to the URL
        URL url = new URL(requestUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        // read the response
        Scanner scan = new Scanner(con.getInputStream());
        String response = "";
        while (scan.hasNext()) {
            response += scan.nextLine();
        }
        scan.close();

        // parse the response to get the longitude and latitude coordinates
        JsonParser parser = new JsonParser();
        JsonObject responseJson = parser.parse(response).getAsJsonObject();
        JsonArray results = responseJson.getAsJsonArray("results");
        JsonObject firstResult = results.get(0).getAsJsonObject();
        JsonObject geometry = firstResult.getAsJsonObject("geometry");
        JsonObject locationJson = geometry.getAsJsonObject("location");
        double lat = locationJson.get("lat").getAsDouble();
        double lng = locationJson.get("lng").getAsDouble();

        return new double[] { lat, lng };
    }

    /*
    Method to get travel time from two locations
     */
    public int getTravelTime(String start, String end) throws IOException {
        String API_KEY = "AIzaSyC6tR-p77Y-NHQyWv7JnRsFlfhOhZvkhTI";
        String requestUrl = "https://maps.googleapis.com/maps/api/directions/json?origin=" +
                start + "&destination=" + end + "&key=" + API_KEY;

        // make the HTTP GET request to the URL
        URL url = new URL(requestUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        // read the response
        Scanner scan = new Scanner(con.getInputStream()); // Create scanner
        String response = "";
        while (scan.hasNext()) {
            response += scan.nextLine();
        }
        scan.close(); // Close scanner

        // parse the response to get the travel time
        JsonParser parser = new JsonParser();
        JsonObject responseJson = parser.parse(response).getAsJsonObject();
        JsonArray routes = responseJson.getAsJsonArray("routes");
        JsonObject firstRoute = routes.get(0).getAsJsonObject();
        JsonArray legs = firstRoute.getAsJsonArray("legs");
        JsonObject firstLeg = legs.get(0).getAsJsonObject();

        return (firstLeg.get("duration").getAsJsonObject().get("value").getAsInt() / 60); // Convert time to minutes and return
    }
}



