package com.aueb.rssidataapp.Triangulation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Instraction {
    private String angle;
    private double distance;
    private String Name;
    private List<Position> points;
    private int sign;
    private long time;
}
