package com.example.meri.golinomedicen.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import com.example.meri.golinomedicen.model.AdressModel;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "medicine_db";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
// create notes table
        db.execSQL(AdressModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + AdressModel.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertAdress(String title,String city,String road,String pelak,String stair,String unit,String lon,String lat) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(AdressModel.COLUMN_Title, title);
        values.put(AdressModel.COLUMN_City, city);
        values.put(AdressModel.COLUMN_Road, road);
        values.put(AdressModel.COLUMN_Pelak, pelak);
        values.put(AdressModel.COLUMN_Stair, stair);
        values.put(AdressModel.COLUMN_Unit, unit);
        values.put(AdressModel.COLUMN_Lat, lat);
        values.put(AdressModel.COLUMN_Long, lon);

        // insert row
        long id = db.insert(AdressModel.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    public AdressModel getAdress(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(AdressModel.TABLE_NAME,
                new String[]{AdressModel.COLUMN_ID, AdressModel.COLUMN_Title, AdressModel.COLUMN_City,AdressModel.COLUMN_Road,AdressModel.COLUMN_Pelak,AdressModel.COLUMN_Stair,AdressModel.COLUMN_Unit, AdressModel.COLUMN_Lat, AdressModel.COLUMN_Long},
                AdressModel.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        Log.e("DatabaseHandler", "getAdress: "+AdressModel.COLUMN_ID+" "+ AdressModel.COLUMN_Title+" "+ AdressModel.COLUMN_City+" "+ AdressModel.COLUMN_Lat+" "+ AdressModel.COLUMN_Long );
        // prepare note object
        AdressModel Adress = new AdressModel(
                cursor.getInt(cursor.getColumnIndex(AdressModel.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(AdressModel.COLUMN_Title)),
                cursor.getString(cursor.getColumnIndex(AdressModel.COLUMN_City)),
                cursor.getString(cursor.getColumnIndex(AdressModel.COLUMN_Road)),
                cursor.getString(cursor.getColumnIndex(AdressModel.COLUMN_Pelak)),
                cursor.getString(cursor.getColumnIndex(AdressModel.COLUMN_Stair)),
                cursor.getString(cursor.getColumnIndex(AdressModel.COLUMN_Unit)),
                cursor.getString(cursor.getColumnIndex(AdressModel.COLUMN_Lat)),
                cursor.getString(cursor.getColumnIndex(AdressModel.COLUMN_Long)));

        // close the db connection
        cursor.close();

        return Adress;
    }

    public ArrayList<AdressModel> getAllAdress() {
        ArrayList<AdressModel> models = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + AdressModel.TABLE_NAME ;//+ " ORDER BY " +
//                AdressModel.COLUMN_Title + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AdressModel model = new AdressModel();
                model.setId(cursor.getInt(cursor.getColumnIndex(AdressModel.COLUMN_ID)));
                model.setTitle(cursor.getString(cursor.getColumnIndex(AdressModel.COLUMN_Title)));
                model.setCity(cursor.getString(cursor.getColumnIndex(AdressModel.COLUMN_City)));
                model.setRoad(cursor.getString(cursor.getColumnIndex(AdressModel.COLUMN_Road)));
                model.setPelak(cursor.getString(cursor.getColumnIndex(AdressModel.COLUMN_Pelak)));
                model.setStair(cursor.getString(cursor.getColumnIndex(AdressModel.COLUMN_Stair)));
                model.setUnit(cursor.getString(cursor.getColumnIndex(AdressModel.COLUMN_Unit)));
                model.setLat(cursor.getString(cursor.getColumnIndex(AdressModel.COLUMN_Lat)));
                model.setLong(cursor.getString(cursor.getColumnIndex(AdressModel.COLUMN_Long)));

                models.add(model);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return models;
    }

    public int getAdressCount() {
        String countQuery = "SELECT  * FROM " + AdressModel.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }
    public void deleteNote(AdressModel adressModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AdressModel.TABLE_NAME, AdressModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(adressModel.getId())});
        db.close();
    }
}
