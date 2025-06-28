package com.example.androidproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidproject.db.DatabaseHelper;
import com.example.androidproject.model.TrafficSign;

import java.util.ArrayList;
import java.util.List;

public class TrafficSignDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public TrafficSignDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    private void open() {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public long insertTrafficSign(TrafficSign sign) {
        open();
        ContentValues values = new ContentValues();
        values.put("code", sign.getCode());
        values.put("name", sign.getName());
        values.put("description", sign.getDescription());
        values.put("image_path", sign.getImagePath());
        values.put("category_id", sign.getCategoryId());
        long id = database.insert("TrafficSign", null, values);
        close();
        return id;
    }

    public TrafficSign getTrafficSignById(int id) {
        open();
        Cursor cursor = database.query("TrafficSign", null, "id = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        TrafficSign sign = null;
        if (cursor.moveToFirst()) {
            sign = cursorToTrafficSign(cursor);
        }
        cursor.close();
        close();
        return sign;
    }

    public TrafficSign getTrafficSignByCode(String code) {
        open();
        Cursor cursor = database.query("TrafficSign", null, "code = ?",
                new String[]{code}, null, null, null);

        TrafficSign sign = null;
        if (cursor.moveToFirst()) {
            sign = cursorToTrafficSign(cursor);
        }
        cursor.close();
        close();
        return sign;
    }

    public List<TrafficSign> getAllTrafficSigns() {
        List<TrafficSign> signs = new ArrayList<>();
        open();
        Cursor cursor = database.query("TrafficSign", null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                signs.add(cursorToTrafficSign(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return signs;
    }

    public List<TrafficSign> getTrafficSignsByCategory(int categoryId) {
        List<TrafficSign> signs = new ArrayList<>();
        open();
        Cursor cursor = database.query("TrafficSign", null, "category_id = ?",
                new String[]{String.valueOf(categoryId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                signs.add(cursorToTrafficSign(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return signs;
    }

    public List<TrafficSign> searchTrafficSigns(String query) {
        List<TrafficSign> signs = new ArrayList<>();
        open();
        String selection = "name LIKE ? OR code LIKE ? OR description LIKE ?";
        String[] selectionArgs = new String[]{"%" + query + "%", "%" + query + "%", "%" + query + "%"};
        Cursor cursor = database.query("TrafficSign", null, selection,
                selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                signs.add(cursorToTrafficSign(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return signs;
    }

    public int updateTrafficSign(TrafficSign sign) {
        open();
        ContentValues values = new ContentValues();
        values.put("code", sign.getCode());
        values.put("name", sign.getName());
        values.put("description", sign.getDescription());
        values.put("image_path", sign.getImagePath());
        values.put("category_id", sign.getCategoryId());

        int rowsAffected = database.update("TrafficSign", values, "id = ?",
                new String[]{String.valueOf(sign.getId())});
        close();
        return rowsAffected;
    }

    public void deleteTrafficSign(int id) {
        open();
        database.delete("TrafficSign", "id = ?",
                new String[]{String.valueOf(id)});
        close();
    }

    private TrafficSign cursorToTrafficSign(Cursor cursor) {
        TrafficSign sign = new TrafficSign();
        sign.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        sign.setCode(cursor.getString(cursor.getColumnIndexOrThrow("code")));
        sign.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        sign.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
        sign.setImagePath(cursor.getString(cursor.getColumnIndexOrThrow("image_path")));
        sign.setCategoryId(cursor.getInt(cursor.getColumnIndexOrThrow("category_id")));
        return sign;
    }
}


