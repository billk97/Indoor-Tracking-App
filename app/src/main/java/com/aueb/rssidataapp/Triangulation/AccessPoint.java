package com.aueb.rssidataapp.Triangulation;

/**
 * Class AccessPoint represents an Access Point with the values we need **/
public class AccessPoint {
    private String ssid; // the name of the network that the access point is connected to
    private String bssid; // the mac address of each access poin
    /**the signal strength the device receives in decibels  **/
    private int level;
    private int TxPower; // the signal strength transmitted by the access point
    private double h; // a constant variable that goes from to 2 to 6 and depends by the environment
    private double x; // the position of the access point in the x access
    private double y; // the position of the access point in the y access

    public AccessPoint(){}

    public AccessPoint(String ssid, String bssid, int TxPower, double h){
        this.ssid=ssid;
        this.bssid=bssid;
        this.TxPower=TxPower;
        this.h=h;
    }

    public AccessPoint(String ssid, String bssid, int level, int TxPower, double h){
        this.ssid=ssid;
        this.bssid=bssid;
        this.level=level;
        this.TxPower=TxPower;
        this.h=h;
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTxPower() {
        return TxPower;
    }

    public void setTxPower(int txPower) {
        TxPower = txPower;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }
    /**Lost propagation function calculates the approximate distance between the device and a access point**/
    public double CalculateDistance(){
        double dividend = -level + TxPower ;
        double divisor = 10*h;
        double power = (dividend/divisor);
        return Math.pow(10,power);
    }

    @Override
    public String toString() {
        return "AccessPoint{" +
                "ssid='" + ssid + '\'' +
                ", bssid='" + bssid + '\'' +
                ", level=" + level +
                ", TxPower=" + TxPower +
                ", h=" + h +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
