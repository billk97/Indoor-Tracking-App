package com.aueb.rssidataapp.Connection;

import com.aueb.rssidataapp.Triangulation.AccessPoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ConnectionHandlerTest {

    @Test
    public void getAccessPointList() {
        ConnectionHandler ch = new ConnectionHandler();
        ch.getAccessPointList();
        AccessPoint ap = new AccessPoint("bill","dd",3,0,4);
        ap.setY(1.2);
        ap.setX(2.0);
        List<AccessPoint> aplist = new ArrayList<>();
        aplist.add(ap);
        List<AccessPoint> accessPoints =null;
        try {
            accessPoints = new ObjectMapper().readValue(ch.getAccessPointList(), new TypeReference<List<AccessPoint>>() {});

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assertEquals(aplist.get(0),accessPoints.get(0));
    }



}