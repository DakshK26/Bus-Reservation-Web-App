package com.web.bus.records;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.bus.HiddenConfig;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;
/**
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: Class to represent bus object
 * Method List:
 * public Bus(String startDestination, String endDestination, int numberOfSeats, String busID) throws IOException
 * public void calculateDistance()
 * public void calculateDistance()
 * public double[] getCoordinates(String location) throws IOException
 * public void getTravelTime() throws IOException
 * public String removeSpaces(String str)
 * getters and setters
 * public String toStringFile()
 * public String toString()
 *  public static void main(String[] args) throws IOException
 **/

public class Bus {
    /*
    Private Instance Data
     */
    private String startDestination;
    private String endDestination;
    private int numberOfSeats;
    private String busID;
    private double startLat;
    private double startLng;
    private double endLat;
    private double endLng;
    private double distance;
    private int timeInMinutes;

    /*
    Constructor to create bus object
     */
    public Bus(String startDestination, String endDestination, int numberOfSeats, String busID) throws IOException {
        this.startDestination = removeSpaces(startDestination);
        this.endDestination = removeSpaces(endDestination);
        this.numberOfSeats = numberOfSeats;
        this.busID = busID;
        var startCords = getCoordinates(startDestination);
        this.startLat = startCords[0];
        this.startLng = startCords[1];
        var endCords = getCoordinates(endDestination);
        this.endLat = endCords[0];
        this.endLng = endCords[1];
        calculateDistance();
        getTravelTime();
    }

    /*
    Method to calculate distance between start and end
     */
    public void calculateDistance() {
        DecimalFormat twoDigits = new DecimalFormat("0.00");

        final int EARTH_RADIUS = 6371; // radius of the Earth in kilometers

        // Convert Distance to Radians
        double startLatRadians = Math.toRadians(this.startLat);
        double endLatRadians = Math.toRadians(this.endLat);
        double latDeltaRadians = Math.toRadians(this.endLat - this.startLat);
        double lngDeltaRadians = Math.toRadians(this.endLng - this.startLng);

        // Use Haversine Formula
        double a = Math.sin(latDeltaRadians / 2) * Math.sin(latDeltaRadians / 2) +
                Math.cos(startLatRadians) * Math.cos(endLatRadians) *
                        Math.sin(lngDeltaRadians / 2) * Math.sin(lngDeltaRadians / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Set distance
        this.distance = EARTH_RADIUS * c;
        this.distance = Math.round(this.distance);
    }

    /*
    Method to find longtitude and langtitude from adress
     */
    public double[] getCoordinates(String location) throws IOException{
        String requestUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                location + "&key=" + HiddenConfig.API_KEY;

        // make the HTTP GET request to the URL
        URL url = new URL(requestUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        // read the response
        Scanner scan = new Scanner(con.getInputStream());
        StringBuilder response = new StringBuilder();
        while (scan.hasNext()) {
            response.append(scan.nextLine());
        }
        scan.close();

        // parse the response to get the longitude and latitude coordinates
        JsonParser parser = new JsonParser();
        JsonObject responseJson = parser.parse(response.toString()).getAsJsonObject();
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
    public void getTravelTime() throws IOException{
        String requestUrl = "https://maps.googleapis.com/maps/api/directions/json?origin=" +
                this.startDestination + "&destination=" + this.endDestination + "&key=" + HiddenConfig.API_KEY;

        // make the HTTP GET request to the URL
        URL url = new URL(requestUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        // read the response
        Scanner scan = new Scanner(con.getInputStream()); // Create scanner
        StringBuilder response = new StringBuilder();
        while (scan.hasNext()) {
            response.append(scan.nextLine());
        }
        scan.close(); // Close scanner

        // parse the response to get the travel time
        JsonParser parser = new JsonParser();
        JsonObject responseJson = parser.parse(response.toString()).getAsJsonObject();
        JsonArray routes = responseJson.getAsJsonArray("routes");
        JsonObject firstRoute = routes.get(0).getAsJsonObject();
        JsonArray legs = firstRoute.getAsJsonArray("legs");
        JsonObject firstLeg = legs.get(0).getAsJsonObject();

        this.timeInMinutes = (firstLeg.get("duration").getAsJsonObject().get("value").getAsInt() / 60); // Convert time to minutes and return
    }

    /*
    Method to remove spaces
     */
    public String removeSpaces(String str) {
        return str.replaceAll("\s", "");
    }

    /*
    Generate Getters and Setters
     */

    public String getStartDestination() {
        return startDestination;
    }

    public void setStartDestination(String startDestination) {
        this.startDestination = startDestination;
    }

    public String getEndDestination() {
        return endDestination;
    }

    public void setEndDestination(String endDestination) {
        this.endDestination = endDestination;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public double getStartLat() {
        return startLat;
    }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    public double getStartLng() {
        return startLng;
    }

    public void setStartLng(double startLng) {
        this.startLng = startLng;
    }

    public double getEndLat() {
        return endLat;
    }

    public void setEndLat(double endLat) {
        this.endLat = endLat;
    }

    public double getEndLng() {
        return endLng;
    }

    public void setEndLng(double endLng) {
        this.endLng = endLng;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(int timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    public String getBusID() {
        return busID;
    }

    public void setBusID(String busID) {
        this.busID = busID;
    }

    public String toStringFile() {
        return (getStartDestination() + "/" + getEndDestination() + "/" + getNumberOfSeats() + "/" + getBusID());
    }

    /*
    toString method
     */
    @Override
    public String toString() {
        return "Bus{" +
                "startDestination='" + startDestination + '\'' +
                ", endDestination='" + endDestination + '\'' +
                ", numberOfSeats='" + numberOfSeats + '\'' +
                "busID='" + busID + '\'' +
                ", startLat=" + startLat +
                ", startLng=" + startLng +
                ", endLat=" + endLat +
                ", endLng=" + endLng +
                ", distance=" + distance +
                ", timeInMinutes=" + timeInMinutes +
                '}';
    }

    /**
     * Self Testing Main Method
     * @param args the command line arguments
     * @throws java.io.IOException
     * */
    public static void main(String[] args) throws IOException {
        Bus bus1 = new Bus("New York City", "Los Angeles", 50, "NYC-LA-01");
        Bus bus2 = new Bus("Chicago", "Houston", 40, "CHI-HOU-01");
        Bus bus3 = new Bus("Boston", "San Francisco", 45, "BOS-SF-01");

        // Test Insert method
        System.out.println("Test Insert Method:");
        System.out.println(bus1);
        System.out.println(bus2);
        System.out.println(bus3);

        // Test Remove spaces method
        System.out.println("Test Remove spaces Method:");
        System.out.println(bus1.removeSpaces("New York City"));
        System.out.println(bus1.removeSpaces(" Los Angeles "));

        // Test getCoordinates method
        System.out.println("Test getCoordinates Method:");
        double[] coordinates = bus1.getCoordinates("New York City");
        System.out.println("Latitude: " + coordinates[0] + ", Longitude: " + coordinates[1]);

        // Test calculateDistance method
        System.out.println("Test calculateDistance Method:");
        bus1.calculateDistance();
        System.out.println(bus1.getDistance() + " kilometers");

        // Test getTravelTime method
        System.out.println("Test getTravelTime Method:");
        bus1.getTravelTime();
        System.out.println(bus1.getTimeInMinutes() + " minutes");

        // Test toStringFile method
        System.out.println("Test toStringFile Method:");
        System.out.println(bus1.toStringFile());

        // Test toString method
        System.out.println("Test toString Method:");
        System.out.println(bus1.toString());

    }


}