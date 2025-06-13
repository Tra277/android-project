package com.example.androidproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidproject.db.DatabaseHelper;
import com.example.androidproject.model.DrivingLicense;

import java.util.ArrayList;
import java.util.List;

public class DrivingLicenseDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DrivingLicenseDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    private void open() {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public long insertDrivingLicense(DrivingLicense drivingLicense) {
        open();
        ContentValues values = new ContentValues();
        values.put("code", drivingLicense.getCode());
        values.put("description", drivingLicense.getDescription());
        values.put("name", drivingLicense.getName());

        long id = database.insert("DrivingLicense", null, values);
        close();
        return id;
    }

    public DrivingLicense getDrivingLicenseById(int id) {
        open();
        Cursor cursor = database.query("DrivingLicense", null, "id = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        DrivingLicense drivingLicense = null;
        if (cursor.moveToFirst()) {
            drivingLicense = cursorToDrivingLicense(cursor);
        }
        cursor.close();
        close();
        return drivingLicense;
    }

    public List<DrivingLicense> getAllDrivingLicenses() {
        List<DrivingLicense> drivingLicenses = new ArrayList<>();
        open();
        Cursor cursor = database.query("DrivingLicense", null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                drivingLicenses.add(cursorToDrivingLicense(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return drivingLicenses;
    }

    public int updateDrivingLicense(DrivingLicense drivingLicense) {
        open();
        ContentValues values = new ContentValues();
        values.put("code", drivingLicense.getCode());
        values.put("description", drivingLicense.getDescription());
        values.put("name", drivingLicense.getName());

        int rowsAffected = database.update("DrivingLicense", values, "id = ?",
                new String[]{String.valueOf(drivingLicense.getId())});
        close();
        return rowsAffected;
    }

    public void deleteDrivingLicense(int id) {
        open();
        database.delete("DrivingLicense", "id = ?",
                new String[]{String.valueOf(id)});
        close();
    }

    private DrivingLicense cursorToDrivingLicense(Cursor cursor) {
        DrivingLicense drivingLicense = new DrivingLicense();
        drivingLicense.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        drivingLicense.setCode(cursor.getString(cursor.getColumnIndexOrThrow("code")));
        drivingLicense.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
        drivingLicense.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        return drivingLicense;
    }
}
