package com.aueb.rssidataapp;

import com.aueb.rssidataapp.Triangulation.Position;

public interface WifiAccessPointCallback {
    public void getCurrentPosition(Position newAccessPoints);
}
