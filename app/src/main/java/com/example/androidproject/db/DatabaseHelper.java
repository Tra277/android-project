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
        db.execSQL("CREATE TABLE Category (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL)");

        db.execSQL("CREATE TABLE Question (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "content TEXT NOT NULL, " +
                "image_path TEXT NOT NULL, " +
                "is_critical_quiz INTEGER NOT NULL, " +
                "is_confusing_quiz INTEGER NOT NULL, " +
                "category_id INTEGER NOT NULL, " +
                "FOREIGN KEY (category_id) REFERENCES Category(id))");

        db.execSQL("CREATE TABLE ExamSet (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "total_correct_answer INTEGER NOT NULL, " +
                "total_wrong_answer INTEGER NOT NULL)");

        db.execSQL("CREATE TABLE Answer (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "is_correct INTEGER NOT NULL, " +
                "content TEXT NOT NULL, " +
                "image_path TEXT NOT NULL, " +
                "question_id INTEGER NOT NULL, " +
                "FOREIGN KEY (question_id) REFERENCES Question(id))");

        db.execSQL("CREATE TABLE ExamSetQuestion (" +
                "question_id INTEGER NOT NULL, " +
                "exam_set_id INTEGER NOT NULL, " +
                "PRIMARY KEY (question_id, exam_set_id), " +
                "FOREIGN KEY (question_id) REFERENCES Question(id), " +
                "FOREIGN KEY (exam_set_id) REFERENCES ExamSet(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Nếu bạn cần nâng cấp DB sau này
        db.execSQL("DROP TABLE IF EXISTS ExamSetQuestion");
        db.execSQL("DROP TABLE IF EXISTS Answer");
        db.execSQL("DROP TABLE IF EXISTS ExamSet");
        db.execSQL("DROP TABLE IF EXISTS Question");
        db.execSQL("DROP TABLE IF EXISTS Category");
        onCreate(db);
    }
}
