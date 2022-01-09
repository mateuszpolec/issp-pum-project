package com.pum.simpleweatherapp;

import java.util.UUID;

public class City {

    private UUID mId;
    private String mCityName;

    public City()
    {
        this.mId = UUID.randomUUID();
        this.mCityName = "London";
    }

    public City(UUID id, String cityName)
    {
        this.mId = id;
        this.mCityName = cityName;
    }

    public void setCityName(String cityName) { this.mCityName = cityName; }
    public String getCityName() { return this.mCityName; }

    public UUID getId() { return mId; }
}
