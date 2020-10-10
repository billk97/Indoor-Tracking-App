package com.aueb.rssidataapp.Triangulation;

import org.ejml.simple.SimpleMatrix;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Triangulate implements Serializable {

    public Position getPossition(List<AccessPoint> accessPoints) {
        int number_of_AccessPoits = accessPoints.size();
        SimpleMatrix A = new SimpleMatrix(number_of_AccessPoits,2);
        SimpleMatrix B = new SimpleMatrix(number_of_AccessPoits,1);
        int i= 0;
        for (AccessPoint ap : accessPoints) {
            double distance = ap.CalculateDistance();
            System.out.println(ap.toString());
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
            System.out.println("Bx: " + Bx);
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


}
