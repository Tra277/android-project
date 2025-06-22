package com.example.androidproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidproject.db.DatabaseHelper;
import com.example.androidproject.model.ExamSet;

import java.util.ArrayList;
import java.util.List;

public class ExamSetDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public ExamSetDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    private void open() {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public long addExamSet(ExamSet examSet) {
        open();
        ContentValues values = new ContentValues();
        values.put("name", examSet.getName());
        values.put("total_correct_answer", examSet.getTotalCorrectAnswer());
        values.put("total_wrong_answer", examSet.getTotalWrongAnswer());
        values.put("is_showed", examSet.isShowed());
        values.put("license_id", examSet.getLicenseId());
        long id = database.insert("ExamSet", null, values);
        close();
        return id;
    }

    public ExamSet getExamSetById(int id) {
        open();
        Cursor cursor = database.query("ExamSet", null, "id = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        ExamSet examSet = null;
        if (cursor.moveToFirst()) {
            examSet = cursorToExamSet(cursor);
        }
        cursor.close();
        close();
        return examSet;
    }

    public List<ExamSet> getAllExamSets() {
        List<ExamSet> examSets = new ArrayList<>();
        open();
        Cursor cursor = database.query("ExamSet", null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                examSets.add(cursorToExamSet(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return examSets;
    }

    public int updateExamSet(ExamSet examSet) {
        open();
        ContentValues values = new ContentValues();
        values.put("name", examSet.getName());
        values.put("total_correct_answer", examSet.getTotalCorrectAnswer());
        values.put("total_wrong_answer", examSet.getTotalWrongAnswer());

        int rowsAffected = database.update("ExamSet", values, "id = ?",
                new String[]{String.valueOf(examSet.getId())});
        close();
        return rowsAffected;
    }

    //Delete all exam sets with is_showed = false
    public void deleteOldExamSets() {
        open();

        // Delete from ExamSetQuestion first
        database.delete("ExamSetQuestion",
                "exam_set_id IN (SELECT id FROM ExamSet WHERE is_showed = ?)",
                new String[]{"0"});

        // Then delete from ExamSet
        database.delete("ExamSet",
                "is_showed = ?",
                new String[]{"0"});

        close();
    }

    public List<ExamSet> getExamSetsByLicenseId(int licenseId) {
        List<ExamSet> examSets = new ArrayList<>();
        open();
        Cursor cursor = database.query("ExamSet", null, "license_id = ?",
                new String[]{String.valueOf(licenseId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                examSets.add(cursorToExamSet(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return examSets;
    }

    public void deleteExamSet(int id) {
        open();
        database.delete("ExamSet", "id = ?",
                new String[]{String.valueOf(id)});
        close();
    }

    private ExamSet cursorToExamSet(Cursor cursor) {
        ExamSet examSet = new ExamSet();
        examSet.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        examSet.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        examSet.setTotalCorrectAnswer(cursor.getInt(cursor.getColumnIndexOrThrow("total_correct_answer")));
        examSet.setTotalWrongAnswer(cursor.getInt(cursor.getColumnIndexOrThrow("total_wrong_answer")));
        examSet.setLicenseId(cursor.getInt(cursor.getColumnIndexOrThrow("license_id")));
        return examSet;
    }
}
