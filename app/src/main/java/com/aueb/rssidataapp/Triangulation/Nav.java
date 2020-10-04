package com.aueb.rssidataapp.Triangulation;

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
public class Nav {
    private double srcLat, srcLon, destLat, destLon, picLat, picLon;

    public Nav(double srcLat, double srcLon, double destLat, double destLon) {
        this.srcLat = srcLat;
        this.srcLon = srcLon;
        this.destLat = destLat;
        this.destLon = destLon;
    }
}
