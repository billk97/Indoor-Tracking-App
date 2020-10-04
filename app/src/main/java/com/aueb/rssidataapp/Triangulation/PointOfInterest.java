package com.aueb.rssidataapp.Triangulation;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PointOfInterest implements Serializable {

    private String name;
    private double lat, lon;
}