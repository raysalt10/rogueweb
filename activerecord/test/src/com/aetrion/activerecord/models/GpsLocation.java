package com.aetrion.activerecord.models;

/**
 * 
 */
public class GpsLocation {

    private String gpsLocation;

    public GpsLocation(String gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public String getLatitude() {
        return gpsLocation.split("x")[0];
    }

    public String getLongitude() {
        return gpsLocation.split("x")[1];
    }

    public boolean equals(Object other) {
        if (other instanceof GpsLocation) {
            return ((GpsLocation) other).getLatitude().equals(
                    getLatitude()) && ((GpsLocation) other).getLongitude().equals(getLongitude());
        }
        return false;
    }

}