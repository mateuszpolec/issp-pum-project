package com.pum.simpleweatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

                String sBuilder = "Temperature: " + res.main.temp;

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
