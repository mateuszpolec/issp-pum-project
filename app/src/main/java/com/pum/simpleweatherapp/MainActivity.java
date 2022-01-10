package com.pum.simpleweatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CityLabAdapter.ItemClickListener {

    private RecyclerView mRecyclerView;
    private List<City> mCities;
    private DBHandler mDbHandler;
    private CityLab mCityLab;
    private CityLabAdapter mCityLabAdapter;

    private SearchView svCitySearchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHandler = new DBHandler(this);
        mCityLab = mCityLab.get(this);
        mCities = mCityLab.getCities();
        initialize();

        mRecyclerView = findViewById(R.id.rvCityLab);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCityLabAdapter = new CityLabAdapter(this);
        mCityLabAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mCityLabAdapter);

        svCitySearchInput = findViewById(R.id.svCitySearchInput);
        svCitySearchInput.setQueryHint("Insert city name to search.");

        svCitySearchInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (mCityLabAdapter.getItemCount() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Results not found", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mCityLabAdapter.getFilter().filter(newText);
                return false;
            }
        });

        svCitySearchInput.setOnCloseListener(() -> {
            initialize();
            mCityLabAdapter.notifyDataSetChanged();
            return false;
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        initialize();
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Intent newActivity = new Intent(view.getContext(), CityActivity.class);
        newActivity.putExtra("CityUUID", mCityLabAdapter.getItem(position).getId().toString());
        view.getContext().startActivity(newActivity);
    }

    public void buttonAddCityClicked(View view)
    {
        Intent newActivity = new Intent(view.getContext(), CityAddActivity.class);
        view.getContext().startActivity(newActivity);
    }

    public void buttonDeleteCityClicked(View view)
    {
        try
        {
            mDbHandler.deleteCity(mCities.get(mCities.size() - 1));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        mCityLabAdapter.notifyDataSetChanged();
    }

    private void initialize()
    {
        Cursor c = mDbHandler.getCities();
        mCities.clear();

        if (c.getCount() != 0)
        {
            while (c.moveToNext())
            {
                UUID uuid = UUID.fromString(c.getString(1));
                String cityName = c.getString(2);
                mCities.add(new City(uuid, cityName));
            }
        }
    }
}