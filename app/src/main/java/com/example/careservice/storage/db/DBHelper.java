package com.example.careservice.storage.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.example.careservice.models.ReadingModel;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "IoTDb.db";
    public static final String READINGS_TABLE_NAME = "readings";
    public static final String READINGS_COLUMN_ID = "id";
    public static final String READINGS_COLUMN_VEHICLE_NUMBER = "vehicle_number";
    public static final String READINGS_COLUMN_VALUE_TYPE = "value_type";
    public static final String READINGS_COLUMN_VALUE = "value";
    public static final String READINGS_COLUMN_LATITUDE = "latitude";
    public static final String READINGS_COLUMN_LONGITUDE = "longitude";
    public static final String READINGS_COLUMN_TIMESTAMP = "timestamp";
    public static final String READINGS_COLUMN_CLOUD_UPLOAD = "cloud_upload";

    private DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    private static DBHelper instance;
    public static DBHelper getInstance(Context applicationContext) {
        if(instance == null) {
            instance = new DBHelper(applicationContext);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + READINGS_TABLE_NAME + " " +
                        "(" +
                        READINGS_COLUMN_ID+" integer primary key, " +
                        READINGS_COLUMN_VEHICLE_NUMBER+"  text, " +
                        READINGS_COLUMN_VALUE_TYPE+"  text, " +
                        READINGS_COLUMN_VALUE+"  text, " +
                        READINGS_COLUMN_LATITUDE+"  text, " +
                        READINGS_COLUMN_LONGITUDE+"  text, " +
                        READINGS_COLUMN_TIMESTAMP+"  text, " +
                        READINGS_COLUMN_CLOUD_UPLOAD+"  integer" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + READINGS_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertReading (ReadingModel reading) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(READINGS_COLUMN_VEHICLE_NUMBER, reading.vehicleNumber);
        contentValues.put(READINGS_COLUMN_VALUE_TYPE, reading.valueType);
        contentValues.put(READINGS_COLUMN_VALUE, reading.value);
        contentValues.put(READINGS_COLUMN_LATITUDE, reading.latitude);
        contentValues.put(READINGS_COLUMN_LONGITUDE, reading.longitude);
        contentValues.put(READINGS_COLUMN_TIMESTAMP, reading.timestamp);
        contentValues.put(READINGS_COLUMN_CLOUD_UPLOAD, reading.cloudUpdate);

        db.insert(READINGS_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ READINGS_TABLE_NAME +
                " where "+ READINGS_COLUMN_ID + "=" + id + "", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, READINGS_TABLE_NAME);
        return numRows;
    }

    public boolean updateReading (ReadingModel reading) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(READINGS_COLUMN_VEHICLE_NUMBER, reading.vehicleNumber);
        contentValues.put(READINGS_COLUMN_VALUE_TYPE, reading.valueType);
        contentValues.put(READINGS_COLUMN_VALUE, reading.value);
        contentValues.put(READINGS_COLUMN_LATITUDE, reading.latitude);
        contentValues.put(READINGS_COLUMN_LONGITUDE, reading.longitude);
        //contentValues.put(READINGS_COLUMN_TIMESTAMP, reading.timestamp);
        contentValues.put(READINGS_COLUMN_CLOUD_UPLOAD, reading.cloudUpdate);

        db.update(READINGS_TABLE_NAME, contentValues,
                READINGS_COLUMN_TIMESTAMP + " = '?' ",
                new String[] { reading.timestamp } );
        return true;
    }

    public boolean updateReading(int cloudUpdate, String timestamp) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(READINGS_COLUMN_CLOUD_UPLOAD, cloudUpdate);

        db.update(READINGS_TABLE_NAME, contentValues,
                READINGS_COLUMN_TIMESTAMP + " = " + timestamp, null );
        return true;
    }

    /*public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }*/

    public List<ReadingModel> getAllCarbonMonoxideReadings() {
        List<ReadingModel> array_list = new ArrayList<ReadingModel>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + READINGS_TABLE_NAME +
                " where "+ READINGS_COLUMN_VALUE_TYPE + " = 'ppm'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            ReadingModel reading = new ReadingModel();

            reading.id = res.getInt(res.getColumnIndex(READINGS_COLUMN_ID));
            reading.vehicleNumber = res.getString(res.getColumnIndex(READINGS_COLUMN_VEHICLE_NUMBER));
            reading.valueType = res.getString(res.getColumnIndex(READINGS_COLUMN_VALUE_TYPE));
            reading.value = res.getString(res.getColumnIndex(READINGS_COLUMN_VALUE));
            reading.latitude = res.getString(res.getColumnIndex(READINGS_COLUMN_LATITUDE));
            reading.longitude = res.getString(res.getColumnIndex(READINGS_COLUMN_LONGITUDE));
            reading.timestamp = res.getString(res.getColumnIndex(READINGS_COLUMN_TIMESTAMP));
            reading.cloudUpdate = res.getInt(res.getColumnIndex(READINGS_COLUMN_CLOUD_UPLOAD));

            array_list.add(reading);
            res.moveToNext();
        }
        return array_list;
    }

    public List<ReadingModel> getAllFluidLevelReadings() {
        List<ReadingModel> array_list = new ArrayList<ReadingModel>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + READINGS_TABLE_NAME +
                " where "+ READINGS_COLUMN_VALUE_TYPE + " = 'level'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            ReadingModel reading = new ReadingModel();

            reading.id = res.getInt(res.getColumnIndex(READINGS_COLUMN_ID));
            reading.vehicleNumber = res.getString(res.getColumnIndex(READINGS_COLUMN_VEHICLE_NUMBER));
            reading.valueType = res.getString(res.getColumnIndex(READINGS_COLUMN_VALUE_TYPE));
            reading.value = res.getString(res.getColumnIndex(READINGS_COLUMN_VALUE));
            reading.latitude = res.getString(res.getColumnIndex(READINGS_COLUMN_LATITUDE));
            reading.longitude = res.getString(res.getColumnIndex(READINGS_COLUMN_LONGITUDE));
            reading.timestamp = res.getString(res.getColumnIndex(READINGS_COLUMN_TIMESTAMP));
            reading.cloudUpdate = res.getInt(res.getColumnIndex(READINGS_COLUMN_CLOUD_UPLOAD));

            array_list.add(reading);
            res.moveToNext();
        }
        return array_list;
    }

    public List<ReadingModel> getAllReadings() {
        List<ReadingModel> array_list = new ArrayList<ReadingModel>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + READINGS_TABLE_NAME + " ORDER BY " + READINGS_COLUMN_ID + " DESC" , null );
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            ReadingModel reading = new ReadingModel();

            reading.id = res.getInt(res.getColumnIndex(READINGS_COLUMN_ID));
            reading.vehicleNumber = res.getString(res.getColumnIndex(READINGS_COLUMN_VEHICLE_NUMBER));
            reading.valueType = res.getString(res.getColumnIndex(READINGS_COLUMN_VALUE_TYPE));
            reading.value = res.getString(res.getColumnIndex(READINGS_COLUMN_VALUE));
            reading.latitude = res.getString(res.getColumnIndex(READINGS_COLUMN_LATITUDE));
            reading.longitude = res.getString(res.getColumnIndex(READINGS_COLUMN_LONGITUDE));
            reading.timestamp = res.getString(res.getColumnIndex(READINGS_COLUMN_TIMESTAMP));
            reading.cloudUpdate = res.getInt(res.getColumnIndex(READINGS_COLUMN_CLOUD_UPLOAD));

            array_list.add(reading);
            res.moveToNext();
        }
        return array_list;
    }
}