package com.mom.momtomom.DTO;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by wee on 2017. 12. 6..
 */

public class PossibleMakerDto  {

    private String name;
    private LatLng latLng;

    public PossibleMakerDto(String name, LatLng latLng) {
        this.name = name;
        this.latLng = latLng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
