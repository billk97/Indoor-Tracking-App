package com.aueb.rssidataapp.Triangulation;

import java.io.Serializable;

public class Position implements Serializable {
    private double X;
    private double Y;

    public Position(double x, double y) {
        X = x;
        Y = y;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }
}
