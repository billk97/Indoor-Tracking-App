package com.aueb.rssidataapp.Connection;

import com.aueb.rssidataapp.Triangulation.AccessPoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ConnectionHandlerTest {



    @Test
    public void getAccessPointList() {
        ConnectionHandler ch = new ConnectionHandler();
        ch.getRequest("access-point");
        AccessPoint ap = new AccessPoint("bill","dd",3,0,4);
        ap.setY(1.2);
        ap.setX(2.0);
        List<AccessPoint> aplist = new ArrayList<>();
        aplist.add(ap);
        List<AccessPoint> accessPoints =null;
        try {
            accessPoints = new ObjectMapper().readValue(ch.getRequest("access-point"), new TypeReference<List<AccessPoint>>() {});

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assertEquals(aplist.get(0),accessPoints.get(0));
    }

    @Test
    public  void getPoiTest(){
        ConnectionHandler ch = new ConnectionHandler();
        String expexted = "[{\"name\":\"kitchen\",\"lat\":38.00767860062,\"lon\":23.71642159024},{\"name\":\"semi\",\"lat\":38.00765939044,\"lon\":23.71638813776},{\"name\":\"living room\",\"lat\":38.00763571277,\"lon\":23.71646099612},{\"name\":\"bedroom n\",\"lat\":38.0076562632,\"lon\":23.71642272422},{\"name\":\"storage\",\"lat\":38.00768132529,\"lon\":23.7164580104},{\"name\":\"bathroom\",\"lat\":38.00763258553,\"lon\":23.71640741546},{\"name\":\"door\",\"lat\":38.00768329148,\"lon\":23.71644625435},{\"name\":\"bedroom s\",\"lat\":38.00763079853,\"lon\":23.71642924462}]";
        assertEquals(expexted, ch.getRequest("poi"));
    }


}