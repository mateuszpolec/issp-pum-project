package com.pum.simpleweatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CityAddActivity extends AppCompatActivity {

    private DBHandler mDbHandler;

    private EditText etCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_add_activity);

        mDbHandler = new DBHandler(this);

    }

    public void buttonAddNewCityClicked(View view)
    {
        etCityName = findViewById(R.id.etCityName);

        if (!etCityName.getText().toString().isEmpty())
        {
            City city = new City();
            city.setCityName(etCityName.getText().toString());

            mDbHandler.addCity(city);
            finish();
        }
        else
        {
            Toast.makeText(this, "Name of city can't be empty.", Toast.LENGTH_SHORT).show();
        }
    }
}
