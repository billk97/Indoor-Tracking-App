package com.aueb.rssidataapp.Triangulation;

import com.aueb.rssidataapp.Connection.ConnectionHandler;

import org.junit.Test;

import static org.junit.Assert.*;

public class TriangulateTest {

    @Test
    public void getPossition() {
        ConnectionHandler cn = new ConnectionHandler();
        AccessPoint ap = new AccessPoint("bill","dd",3,4);
        ap.setY(1.2);
        ap.setY(2.0);
        assertEquals(ap.toString(),cn.getRequest("access-point").toString());
    }
}