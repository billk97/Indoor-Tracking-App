package com.aueb.rssidataapp.Triangulation;

import org.ejml.simple.SimpleMatrix;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Triangulate implements Serializable {

    public Position getPossition(List<AccessPoint> accessPoints) {
        int numberOfAccessPoints = accessPoints.size();
        SimpleMatrix A = new SimpleMatrix(numberOfAccessPoints, 2);
        SimpleMatrix B = new SimpleMatrix(numberOfAccessPoints, 1);
        int i= 0;
        for (AccessPoint ap : accessPoints) {
            double distance = ap.CalculateDistance();
            System.out.println(ap.toString());
            double Ax = 2 * (ap.getX() - accessPoints.get(numberOfAccessPoints - 1).getX());
            double Ay = 2 * (ap.getY() - accessPoints.get(numberOfAccessPoints - 1).getY());
            A.set(i,0,Ax);
            A.set(i,1,Ay);
            System.out.println("A: " + Ax + "x0");
            System.out.println("A: " + Ay + "y0");
            double Bx = Math.pow(ap.getX(), 2) +
                    Math.pow(ap.getY(), 2) -
                    Math.pow(accessPoints.get(numberOfAccessPoints - 1).getX(), 2) -
                    Math.pow(accessPoints.get(numberOfAccessPoints - 1).getY(), 2) -
                    Math.pow(distance, 2) +
                    Math.pow(accessPoints.get(numberOfAccessPoints - 1).CalculateDistance(), 2);
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

    public double[] getLevenbergPosition(List<AccessPoint> accessPoints) {
        List<double[]> tempList = new ArrayList<>();
        List<Double> tempDistances = new ArrayList<>();
        for (AccessPoint ap : accessPoints) {
            tempList.add(new double[]{ap.getX(), ap.getY()});
            tempDistances.add(ap.CalculateDistance());
        }
        double[][] accessPointsPositions = tempList.toArray(new double[tempList.size()][2]);
        // double[] distances = tempDistances.toArray();

        return new double[]{0.0, 0.0};
    }


}
