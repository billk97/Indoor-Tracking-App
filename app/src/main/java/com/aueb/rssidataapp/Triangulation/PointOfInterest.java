package com.aueb.rssidataapp.Triangulation;

import java.io.Serializable;

public class PointOfInterest implements Serializable {

    private String name;
    private double lat, lon;

    public PointOfInterest(){};
    public PointOfInterest(String name, double lat, double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "PointOfInterest{" +
                "name='" + name + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}