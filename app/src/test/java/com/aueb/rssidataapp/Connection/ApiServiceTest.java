package com.aueb.rssidataapp.Connection;

import com.aueb.rssidataapp.Triangulation.AccessPoint;
import com.aueb.rssidataapp.Triangulation.Nav;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ApiServiceTest {



    @Test
    public void getAccessPointList() {
        ApiService ch = new ApiService();
        ch.getAccessPoints();
        AccessPoint ap = new AccessPoint("billk97", "dc:a6:32:2a:02:30", -30, 0, 4.5);
        ap.setY(23.716426);
        ap.setX(38.007643);
        List<AccessPoint> aplist = new ArrayList<>();
        aplist.add(ap);
        List<AccessPoint> accessPoints =null;
        try {
            accessPoints = new ObjectMapper().readValue(ch.getAccessPoints(), new TypeReference<List<AccessPoint>>() {
            });

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assertEquals(aplist.get(0),accessPoints.get(0));
    }

    @Test
    public  void getPoiTest(){
        ApiService ch = new ApiService();
        String expexted = "[{\"name\":\"kitchen\",\"lat\":38.00767860062,\"lon\":23.71642159024},{\"name\":\"semi\",\"lat\":38.00765939044,\"lon\":23.71638813776},{\"name\":\"living room\",\"lat\":38.00763571277,\"lon\":23.71646099612},{\"name\":\"bedroom n\",\"lat\":38.0076562632,\"lon\":23.71642272422},{\"name\":\"storage\",\"lat\":38.00768132529,\"lon\":23.7164580104},{\"name\":\"bathroom\",\"lat\":38.00763258553,\"lon\":23.71640741546},{\"name\":\"door\",\"lat\":38.00768329148,\"lon\":23.71644625435},{\"name\":\"bedroom s\",\"lat\":38.00763079853,\"lon\":23.71642924462}]";
        assertEquals(expexted, ch.getPois());
    }
    @Test
    public void getDirections(){
        ApiService ch = new ApiService();
        String expected = "{\"distance\":11.0,\"time\":7714,\"instructions\":[{\"points\":{\"size\":1,\"empty\":false,\"dimension\":2,\"immutable\":false,\"3D\":false},\"annotation\":{\"empty\":true,\"importance\":0,\"message\":\"\"},\"sign\":0,\"name\":\"main-semi\",\"distance\":2.856129568763875,\"time\":2056,\"length\":1,\"extraInfoJSON\":{\"heading\":115.73}},{\"points\":{\"size\":1,\"empty\":false,\"dimension\":2,\"immutable\":false,\"3D\":false},\"annotation\":{\"empty\":true,\"importance\":0,\"message\":\"\"},\"sign\":1,\"name\":\"main-bedn\",\"distance\":0.771,\"time\":555,\"length\":1,\"extraInfoJSON\":{}},{\"points\":{\"size\":1,\"empty\":false,\"dimension\":2,\"immutable\":false,\"3D\":false},\"annotation\":{\"empty\":true,\"importance\":0,\"message\":\"\"},\"sign\":-2,\"name\":\"main-beds\",\"distance\":2.282,\"time\":1643,\"length\":1,\"extraInfoJSON\":{}},{\"points\":{\"size\":2,\"empty\":false,\"dimension\":2,\"immutable\":true,\"3D\":false},\"annotation\":{\"empty\":true,\"importance\":0,\"message\":\"\"},\"sign\":0,\"name\":\"hall-living\",\"distance\":4.129,\"time\":2971,\"length\":2,\"extraInfoJSON\":{}},{\"points\":{\"size\":1,\"empty\":false,\"dimension\":2,\"immutable\":true,\"3D\":false},\"annotation\":{\"empty\":true,\"importance\":0,\"message\":\"\"},\"sign\":-2,\"name\":\"door-hall\",\"distance\":0.68,\"time\":489,\"length\":1,\"extraInfoJSON\":{}},{\"points\":{\"size\":1,\"empty\":false,\"dimension\":2,\"immutable\":false,\"3D\":false},\"annotation\":{\"empty\":true,\"importance\":0,\"message\":\"\"},\"sign\":4,\"name\":\"\",\"distance\":0.0,\"time\":0,\"length\":0,\"extraInfoJSON\":{\"last_heading\":355.709387951069}}],\"points\":{\"size\":7,\"empty\":false,\"dimension\":2,\"immutable\":true,\"3D\":false}}";
        Nav nav = new Nav(38.00765939044, 23.71638813776, 38.00768329148, 23.71644625435);
        try {
            assertEquals(expected, ch.NavInstructions("nav", nav));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}