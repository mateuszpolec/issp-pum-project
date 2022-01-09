package com.pum.simpleweatherapp;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CityLab {

    private static CityLab sCityLab;
    private List<City> mCities;

    public static CityLab get(Context context)
    {
        if (sCityLab == null)
        {
            sCityLab = new CityLab(context);
        }

        return sCityLab;
    }

    private CityLab(Context context)
    {
        mCities = new ArrayList<>();
    }

    public List<City> getCities()
    {
        return mCities;
    }

    public City getCity(UUID uuid)
    {
        for (City c : mCities)
        {
            if (c.getId().equals(uuid))
            {
                return c;
            }
        }
        return null;
    }

    public void addCity(City newCity)
    {
        mCities.add(newCity);
    }

    public void deleteCity(UUID uuid)
    {
        for (City c : mCities)
        {
            if (c.getId().equals(uuid))
            {
                mCities.remove(c);
                break;
            }
        }
    }
}
