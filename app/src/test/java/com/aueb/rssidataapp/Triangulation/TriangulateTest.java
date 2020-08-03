package com.aueb.rssidataapp.Triangulation;

import com.aueb.rssidataapp.Connection.ApiService;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TriangulateTest {

    @Test
    public void getPossition() {
        ApiService cn = new ApiService();
        AccessPoint ap = new AccessPoint("bill","dd",3,4);
        ap.setY(1.2);
        ap.setY(2.0);
        assertEquals(ap.toString(),cn.getRequest("access-point").toString());
    }
    @Test
    public void getCastomePossition() {
        AccessPoint ap = new AccessPoint("bill","pf",-15,4.5);
        ap.setX(1.2);
        ap.setY(2.0);
        AccessPoint ap1 = new AccessPoint("bill","35",-15,4.5);
        ap1.setX(2.7);
        ap1.setY(0.6);
        AccessPoint ap2 = new AccessPoint("bill","11",-15,4.5);
        ap2.setX(5.1);
        ap2.setY(5.1);
        AccessPoint ap3 = new AccessPoint("bill","22",-15,4.5);
        ap3.setX(6.2);
        ap3.setY(0.6);
        AccessPoint ap4 = new AccessPoint("bill","8c",-15,4.5);
        ap4.setX(2.0);
        ap4.setY(2.5);
        List<AccessPoint> aplist = new ArrayList<>();
        aplist.add(ap1);
        aplist.add(ap2);
        aplist.add(ap3);
        aplist.add(ap4);
        Triangulate tr = new Triangulate();
        System.out.println(tr.getPossition(aplist));
        assertEquals(4.1,tr.getPossition(aplist).getX());
    }
}