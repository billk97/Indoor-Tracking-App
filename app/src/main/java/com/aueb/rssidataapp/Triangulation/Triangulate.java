package com.aueb.rssidataapp.Triangulation;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.List;

public class Triangulate {
    /**this function returns the X and Y position of the phone
     * @param accessPoints  a list of accessPoints type AccessPoint
     * @return returns an Object type Position with two values (x,y)
     *
     * **/
    public Position getPossition(List<AccessPoint> accessPoints) {
        int number_of_AccessPoits = accessPoints.size();
        SimpleMatrix A = new SimpleMatrix(number_of_AccessPoits,2);
        SimpleMatrix B = new SimpleMatrix(number_of_AccessPoits,1);
        int i= 0;
        for (AccessPoint ap : accessPoints) {
            double distance = ap.CalculateDistance();
            System.out.println("ap.CalculateDistance(); " + ap.CalculateDistance());
            System.out.println("ap.getX() " + ap.getX());
            System.out.println("accessPoints.get(accessPoints.size()).getX() " + accessPoints.get(number_of_AccessPoits- 1).getX());

            double Ax = 2 * (ap.getX() - accessPoints.get(number_of_AccessPoits - 1).getX());
            double Ay = 2 * (ap.getY() - accessPoints.get(number_of_AccessPoits - 1).getY());
            A.set(i,0,Ax);
            A.set(i,1,Ay);
            System.out.println("A: " + Ax + "x0");
            System.out.println("A: " + Ay + "y0");
            double Bx = Math.pow(ap.getX(), 2) +
                    Math.pow(ap.getY(), 2) -
                    Math.pow(accessPoints.get( number_of_AccessPoits- 1).getX(), 2) -
                    Math.pow(accessPoints.get(number_of_AccessPoits- 1).getY(), 2) -
                    Math.pow(distance, 2) +
                    Math.pow(accessPoints.get(number_of_AccessPoits- 1).CalculateDistance(), 2);
            System.out.println("B:" + Bx);
            B.set(i,0,Bx);
            System.out.println("===========================================");
            i++;
        }

        SimpleMatrix At = A.transpose().mult(A).invert().mult(A.transpose()).mult(B);
        A.print();
        B.print();
        At.print();
        System.out.println(At.get(0,0));
        System.out.println(At.get(1,0));
        return new Position(At.get(0,0),At.get(1,0));
    }
    public static void main(String[] args) {
        Triangulate tr = new Triangulate();
        List<AccessPoint> listofap = new ArrayList<AccessPoint>();
        AccessPoint ap = new AccessPoint("ssid","ROUTER",-44,-30,3 );
        ap.setX(0);
        ap.setY(0);
        listofap.add(ap);

        AccessPoint ap1 = new AccessPoint("ssid","rasbary",-42,-30,3 );
        ap1.setX(-2.1);
        ap1.setY(0);
        listofap.add(ap1);

        AccessPoint ap2 = new AccessPoint("ssid","mobile",-41,-30,3 );
        ap2.setX(-0.5);
        ap2.setY(-2.3);
        listofap.add(ap2);
        tr.getPossition(listofap);

//        AccessPoint ap3 = new AccessPoint("ssid","mac",-55,-30,3 );
//        ap3.setX(2.5);
//        ap3.setY(2);
//        listofap.add(ap3);
//        tr.getPossition(listofap);
    }
}
