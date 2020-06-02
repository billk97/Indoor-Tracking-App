package com.aueb.rssidataapp.Triangulation;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccessPointTest {

    @Test
    public void calculateDistance() {
        AccessPoint ap = new AccessPoint();
        ap.setH(4.5);
        ap.setLevel(-35);
        ap.setTxPower(-19);
        System.out.println(ap.CalculateDistance());
        assertEquals(Math.round(ap.CalculateDistance()),3.7);

    }
}