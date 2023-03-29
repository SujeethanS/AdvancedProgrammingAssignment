package com.example.customerOrderApp.pojo;

/**
 * Created by kalemkt2 on 12/14/2017.
 */

public class Location {
    private int id;
    private String locationName = "Main Store";
    private String locationAddress = "n/l";
    private Integer locationId = 1 ;

    public Location() {
    }

    public Location(String locationName, String locationAddress) {
        this.locationName = locationName;
        this.locationAddress = locationAddress;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
