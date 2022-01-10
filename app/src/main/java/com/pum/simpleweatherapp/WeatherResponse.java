package com.pum.simpleweatherapp;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WeatherResponse {

    @SerializedName("coord")
    public Coord coord;

    @SerializedName("main")
    public Main main;

    @SerializedName("name")
    public String name;

    @SerializedName("weather")
    public ArrayList<Weather> weather = new ArrayList<Weather>();

    @SerializedName("wind")
    public Wind wind;

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
        public int pressure;

        @SerializedName("humidity")
        public float humidity;
    }
    
    class Weather
    {
        @SerializedName("id")
        public int id;

        @SerializedName("main")
        public String main;

        @SerializedName("description")
        public String description;

        @SerializedName("icon")
        public String icon;
    }

    class Wind
    {
        @SerializedName("speed")
        public float speed;

        @SerializedName("degrees")
        public float degrees;
    }

    class Coord
    {
        @SerializedName("lon")
        public float lon;

        @SerializedName("lat")
        public float lat;
    }
}
