package com.pum.simpleweatherapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CityActivity extends AppCompatActivity {

    private CityLab mCityLab;
    private DBHandler mDbHandler;
    private City mCurrentCity;

    private TextView tvCityName;
    private ImageView ivCityWeatherIcon;
    private TextView tvWeatherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_activity);

        mDbHandler = new DBHandler(this);

        mCityLab = mCityLab.get(this);

        UUID cityUUID = UUID.fromString(getIntent().getStringExtra("CityUUID"));
        mCurrentCity = mCityLab.getCity(cityUUID);

        tvCityName = findViewById(R.id.tvCityName);
        tvCityName.setText(mCurrentCity.getCityName());

        ivCityWeatherIcon = findViewById(R.id.ivCityWeatherIcon);

        tvWeatherInfo = findViewById(R.id.tvWeatherInfo);


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        WeatherApiClient client = retrofit.create(WeatherApiClient.class);
        Call<WeatherResponse> call = client.getCurrentWeatherForCity(mCurrentCity.getCityName(), "metric", "6868ac072119974283ff81f872abf1af");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response)
            {
                WeatherResponse res = response.body();

                assert res != null;

                String sBuilder = "Temperature: " +
                        res.main.temp +
                        "\n" +
                        "Feels like: " +
                        res.main.feels_like +
                        "\n" +
                        "Description: " +
                        res.weather.get(0).description +
                        "\n" +
                        "Wind speed: " + res.wind.speed + "m/s from: " + res.wind.degrees +
                        "\n" +
                        "Pressure: " + res.main.pressure + "hPa";

                String weatherIconUrl = String.format("https://openweathermap.org/img/wn/%s@4x.png", res.weather.get(0).icon);

                Picasso.get().load(weatherIconUrl).into(ivCityWeatherIcon);

                tvWeatherInfo.setText(sBuilder);
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t)
            {
                System.out.println(t.getMessage());
            }
        });
    }

    public void buttonCityDeleteThisClicked(View view)
    {
        mDbHandler.deleteCity(mCurrentCity);
        finish();
    }
}
