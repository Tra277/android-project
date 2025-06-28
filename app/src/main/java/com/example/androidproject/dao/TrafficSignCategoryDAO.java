package com.example.androidproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidproject.db.DatabaseHelper;
import com.example.androidproject.model.TrafficSignCategory;

import java.util.ArrayList;
import java.util.List;

public class TrafficSignCategoryDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public TrafficSignCategoryDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    private void open() {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public long insertTrafficSignCategory(TrafficSignCategory category) {
        open();
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        long id = database.insert("TrafficSignCategory", null, values);
        close();
        return id;
    }

    public TrafficSignCategory getTrafficSignCategoryById(int id) {
        open();
        Cursor cursor = database.query("TrafficSignCategory", null, "id = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        TrafficSignCategory category = null;
        if (cursor.moveToFirst()) {
            category = cursorToTrafficSignCategory(cursor);
        }
        cursor.close();
        close();
        return category;
    }

    public List<TrafficSignCategory> getAllTrafficSignCategories() {
        List<TrafficSignCategory> categories = new ArrayList<>();
        open();
        Cursor cursor = database.query("TrafficSignCategory", null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                categories.add(cursorToTrafficSignCategory(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return categories;
    }

    public int updateTrafficSignCategory(TrafficSignCategory category) {
        open();
        ContentValues values = new ContentValues();
        values.put("name", category.getName());

        int rowsAffected = database.update("TrafficSignCategory", values, "id = ?",
                new String[]{String.valueOf(category.getId())});
        close();
        return rowsAffected;
    }

    public void deleteTrafficSignCategory(int id) {
        open();
        database.delete("TrafficSignCategory", "id = ?",
                new String[]{String.valueOf(id)});
        close();
    }

    private TrafficSignCategory cursorToTrafficSignCategory(Cursor cursor) {
        TrafficSignCategory category = new TrafficSignCategory();
        category.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        category.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        return category;
    }
}


