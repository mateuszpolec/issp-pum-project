package com.pum.simpleweatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.UUID;

public class DBHandler extends SQLiteOpenHelper {

    CityLab mCityLab;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "CityDB.db";

    public static final String TABLE_NAME = "cities";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CITY_ID = "city_id";
    public static final String COLUMN_CITY_NAME = "city_name";

    public DBHandler(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        mCityLab = CityLab.get(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String INITIALIZE_TABLE = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)", TABLE_NAME, COLUMN_ID, COLUMN_CITY_ID, COLUMN_CITY_NAME
        );

        db.execSQL(INITIALIZE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
        onCreate(db);
    }

    public Cursor getCities()
    {
        String q = String.format("SELECT * FROM %s", TABLE_NAME);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(q, null);
    }

    public Cursor getCity(UUID uuid)
    {
        String uuidString = uuid.toString();
        String q = String.format("SELECT * FROM %s WHERE = ?", TABLE_NAME);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(q, new String[]{String.valueOf(uuidString)});
    }

    public void addCity(City city)
    {
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CITY_ID, city.getId().toString());
        cv.put(COLUMN_CITY_NAME, city.getCityName());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, cv);
        db.close();

        mCityLab.addCity(city);
    }

    public void deleteCity(City city)
    {
        String uuidString = city.getId().toString();
        String q = String.format("SELECT * FROM %s WHERE %s = %s", TABLE_NAME, COLUMN_CITY_ID, uuidString);

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_CITY_ID + " = ?", new String[]{String.valueOf(uuidString)});
        db.close();

        mCityLab.deleteCity(city.getId());
    }

    public void updateCity(City city)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CITY_NAME, city.getCityName());

        db.update(TABLE_NAME, cv, COLUMN_CITY_ID + " = ?", new String[]{String.valueOf(city.getId().toString())});
        db.close();
    }
}
