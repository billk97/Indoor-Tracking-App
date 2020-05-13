package com.aueb.rssidataapp.Triangulation;

public class Nav {
    private double srcLat, srcLon, destLat, destLon, picLat, picLon;
    public Nav(){}

    public Nav(double srcLat, double srcLon, double destLat, double destLon) {
        this.srcLat = srcLat;
        this.srcLon = srcLon;
        this.destLat = destLat;
        this.destLon = destLon;
    }



    public double getSrcLat() {
        return srcLat;
    }

    public void setSrcLat(double srcLat) {
        this.srcLat = srcLat;
    }

    public double getSrcLon() {
        return srcLon;
    }

    public void setSrcLon(double srcLon) {
        this.srcLon = srcLon;
    }

    public double getDestLat() {
        return destLat;
    }

    public void setDestLat(double destLat) {
        this.destLat = destLat;
    }

    public double getDestLon() {
        return destLon;
    }

    public void setDestLon(double destLon) {
        this.destLon = destLon;
    }

    public double getPicLat() {
        return picLat;
    }

    public void setPicLat(double picLat) {
        this.picLat = picLat;
    }

    public double getPicLon() {
        return picLon;
    }

    public void setPicLon(double picLon) {
        this.picLon = picLon;
    }
    @Override
    public String toString() {
        return "Nav{" +
                "srcLat=" + srcLat +
                ", srcLon=" + srcLon +
                ", destLat=" + destLat +
                ", destLon=" + destLon +
                ", picLat=" + picLat +
                ", picLon=" + picLon +
                '}';
    }
}
