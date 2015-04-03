package com.nexhop.model;

/**
 * Created by vijayalaxmi on 16/3/15.
 */
public class LocationBean {
    Double latitude=12.9667; //default set to Bangalore location
    Double longitude=77.5667;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
