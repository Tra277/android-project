package com.example.androidproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "QuizApp.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create DrivingLicense table
        db.execSQL("CREATE TABLE DrivingLicense (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "code TEXT NOT NULL," +
                "name TEXT NOT NULL" +
                ")");

        // Create Category table
        db.execSQL("CREATE TABLE Category (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "license_id INTEGER NOT NULL," +
                "FOREIGN KEY (license_id) REFERENCES DrivingLicense(id)" +
                ")");

        // Create Question table
        db.execSQL("CREATE TABLE Question (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "content TEXT NOT NULL," +
                "image_path TEXT NOT NULL," +
                "is_critical_quiz INTEGER NOT NULL," +       // BOOLEAN as INTEGER 0 or 1
                "is_confusing_quiz INTEGER NOT NULL," +
                "category_id INTEGER NOT NULL," +
                "FOREIGN KEY (category_id) REFERENCES Category(id)" +
                ")");

        // Create Answer table
        db.execSQL("CREATE TABLE Answer (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "is_correct INTEGER NOT NULL," +              // BOOLEAN as INTEGER
                "content TEXT NOT NULL," +
                "image_path TEXT NOT NULL," +
                "question_id INTEGER NOT NULL," +
                "FOREIGN KEY (question_id) REFERENCES Question(id)" +
                ")");

        // Create ExamSet table
        db.execSQL("CREATE TABLE ExamSet (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "total_correct_answer INTEGER NOT NULL," +
                "total_wrong_answer INTEGER NOT NULL" +
                ")");

        // Create ExamSetQuestion table (junction table)
        db.execSQL("CREATE TABLE ExamSetQuestion (" +
                "question_id INTEGER NOT NULL," +
                "exam_set_id INTEGER NOT NULL," +
                "PRIMARY KEY (question_id, exam_set_id)," +
                "FOREIGN KEY (question_id) REFERENCES Question(id)," +
                "FOREIGN KEY (exam_set_id) REFERENCES ExamSet(id)" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables
        db.execSQL("DROP TABLE IF EXISTS ExamSetQuestion");
        db.execSQL("DROP TABLE IF EXISTS Answer");
        db.execSQL("DROP TABLE IF EXISTS Question");
        db.execSQL("DROP TABLE IF EXISTS Category");
        db.execSQL("DROP TABLE IF EXISTS ExamSet");
        db.execSQL("DROP TABLE IF EXISTS DrivingLicense");
        // Recreate tables
        onCreate(db);
    }
}
