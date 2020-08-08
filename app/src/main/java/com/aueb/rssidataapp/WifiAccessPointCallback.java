package com.aueb.rssidataapp;

import android.net.wifi.ScanResult;

import java.util.List;

public interface WifiAccessPointCallback {
    public void updateAvailableAccessPoints(List<ScanResult> newAccessPoints);
}
