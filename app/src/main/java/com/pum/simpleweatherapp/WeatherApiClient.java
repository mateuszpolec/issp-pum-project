package com.pum.simpleweatherapp;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface WeatherApiClient {
    @GET("data/2.5/weather?")
    Call<WeatherResponse> getCurrentWeatherForCity(@Query("q") String city, @Query("units") String units, @Query("appid") String API_KEY);

}
