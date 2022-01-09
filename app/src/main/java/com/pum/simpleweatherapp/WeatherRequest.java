package com.pum.simpleweatherapp;

import android.content.Context;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRequest {

    public String mResponse;

    public String RequestForCity(String string)
    {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        WeatherApiClient client = retrofit.create(WeatherApiClient.class);
        Call<WeatherResponse> call = client.getCurrentWeatherForCity("Wrocław", "metric", "6868ac072119974283ff81f872abf1af");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response)
            {
                WeatherResponse res = response.body();

                assert res != null;

                String sBuilder = res.name;
                mResponse = sBuilder;
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t)
            {
                System.out.println(t.getMessage());
            }
        });

        return mResponse;
    }
}
