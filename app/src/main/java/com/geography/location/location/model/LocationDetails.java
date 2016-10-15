package com.geography.location.location.model;

/**
 * Created by Admin on 15-10-2016.
 * 
 */

public class LocationDetails {

    private String time;
    private String latitude;
    private String longitude;

    public LocationDetails()
    {

    }

    public LocationDetails(String time,String latitude,String longitude)
    {
        this.time=time;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
