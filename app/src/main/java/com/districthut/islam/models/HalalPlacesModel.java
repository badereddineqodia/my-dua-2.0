package com.districthut.islam.models;

public class HalalPlacesModel {


    String placeName;
    String placeDescription;
    String placeDistance;

    public HalalPlacesModel(String placeName, String placeDescription, String placeDistance) {
        this.placeName = placeName;
        this.placeDescription = placeDescription;
        this.placeDistance = placeDistance;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public String getPlaceDistance() {
        return placeDistance;
    }

    public void setPlaceDistance(String placeDistance) {
        this.placeDistance = placeDistance;
    }

}
