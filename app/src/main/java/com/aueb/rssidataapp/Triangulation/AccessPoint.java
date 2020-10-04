package com.aueb.rssidataapp.Triangulation;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessPoint implements Serializable {
    private String ssid; // the name of the network that the access point is connected to
    private String bssid; // the mac address of each access poin
    /**the signal strength the device receives in decibels  **/
    private int level;
    private int TxPower; // the signal strength transmitted by the access point
    private double h; // a constant variable that goes from to 2 to 6 and depends by the environment
    private double x; // the position of the access point in the x access
    private double y; // the position of the access point in the y access


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccessPoint)) return false;
        AccessPoint that = (AccessPoint) o;
        return level == that.level &&
                TxPower == that.TxPower &&
                Double.compare(that.h, h) == 0 &&
                Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0 &&
                ssid.equals(that.ssid) &&
                bssid.equals(that.bssid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ssid, bssid, level, TxPower, h, x, y);
    }

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

    /**Lost propagation function calculates the approximate distance between the device and a access point**/
    public double CalculateDistance(){
        double dividend = -level + TxPower ;
        double divisor = 10*h;
        double power = (dividend/divisor);
        return Math.pow(10,power);
    }


}
