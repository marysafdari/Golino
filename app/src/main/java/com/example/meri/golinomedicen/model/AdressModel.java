package com.example.meri.golinomedicen.model;

public class AdressModel {
    int Id;
    String Title;
    String City;
    String Road;// خیابان
    String Pelak;//پلاک
    String Stair;//طبقه
    String Unit;//واحد
    String Lat;
    String Long;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getRoad() {
        return Road;
    }

    public void setRoad(String road) {
        Road = road;
    }

    public String getPelak() {
        return Pelak;
    }

    public void setPelak(String pelak) {
        Pelak = pelak;
    }

    public String getStair() {
        return Stair;
    }

    public void setStair(String stair) {
        Stair = stair;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }

    public AdressModel() {
    }

    public AdressModel(int id, String title, String city, String road, String pelak, String stair, String unit, String lat, String lon) {
        this.Id = id;
        this.Title = title;
        this.City = city;
        this.Road = road;
        this.Pelak = pelak;
        this.Stair = stair;
        this.Unit = unit;
        this.Lat = lat;
        this.Long = lon;
    }

    // Create table SQL query

    public static final String TABLE_NAME = "adress";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_Title = "title";
    public static final String COLUMN_Lat = "lat";
    public static final String COLUMN_Long = "long";
    public static final String COLUMN_City = "city";
    public static final String COLUMN_Road = "road";
    public static final String COLUMN_Pelak = "pelak";
    public static final String COLUMN_Stair = "stair";
    public static final String COLUMN_Unit = "unit";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_Title + " TEXT,"
                    + COLUMN_Road + " TEXT,"
                    + COLUMN_Pelak + " TEXT,"
                    + COLUMN_Stair + " TEXT,"
                    + COLUMN_Unit + " TEXT,"
                    + COLUMN_Lat + " TEXT,"
                    + COLUMN_Long + " TEXT,"
                    + COLUMN_City + " TEXT"
                    + ")";

}
