package com.pum.simpleweatherapp;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

    @SerializedName("coord")
    public Coord coord;

    @SerializedName("main")
    public Main main;

    @SerializedName("name")
    public String name;

    class Main
    {
        @SerializedName("temp")
        public float temp;

        @SerializedName("feels_like")
        public float feels_like;

        @SerializedName("temp_max")
        public float temp_max;

        @SerializedName("temp_min")
        public float temp_min;

        @SerializedName("pressure")
        public float pressure;

        @SerializedName("humidity")
        public float humidity;
    }

    class Coord
    {
        @SerializedName("lon")
        public float lon;

        @SerializedName("lat")
        public float lat;
    }
}
