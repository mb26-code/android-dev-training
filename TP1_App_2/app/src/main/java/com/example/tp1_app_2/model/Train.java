package com.example.tp1_app_2.model;

public class Train {
    String departureTown;
    String arrivalTown;
    String departureTime;
    String arrivalTime;
    String departurePlatform;
    String arrivalPlatform;

    public Train(String departureTown, String arrivalTown, String departureTime, String arrivalTime, String departurePlatform, String arrivalPlatform) {
        this.departureTown = departureTown;
        this.arrivalTown = arrivalTown;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departurePlatform = departurePlatform;
        this.arrivalPlatform = arrivalPlatform;
    }

    public String getDepartureTown() { return departureTown; }
    public String getArrivalTown() { return arrivalTown; }
    public String getDepartureTime() { return departureTime; }
    public String getArrivalTime() { return arrivalTime; }
    public String getDeparturePlatform() { return departurePlatform; }
    public String getArrivalPlatform() { return arrivalPlatform; }
}
