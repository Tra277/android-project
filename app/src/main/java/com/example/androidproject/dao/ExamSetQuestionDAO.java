package com.example.androidproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidproject.db.DatabaseHelper;
import com.example.androidproject.model.ExamSetQuestion;

import java.util.ArrayList;
import java.util.List;

public class ExamSetQuestionDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public ExamSetQuestionDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    private void open() {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public long insertExamSetQuestion(ExamSetQuestion examSetQuestion) {
        open();
        ContentValues values = new ContentValues();
        values.put("question_id", examSetQuestion.getQuestionId());
        values.put("exam_set_id", examSetQuestion.getExamSetId());

        long result = database.insert("ExamSetQuestion", null, values);
        close();
        return result;
    }

    public ExamSetQuestion getExamSetQuestion(int questionId, int examSetId) {
        open();
        Cursor cursor = database.query("ExamSetQuestion", null,
                "question_id = ? AND exam_set_id = ?",
                new String[]{String.valueOf(questionId), String.valueOf(examSetId)},
                null, null, null);

        ExamSetQuestion examSetQuestion = null;
        if (cursor.moveToFirst()) {
            examSetQuestion = cursorToExamSetQuestion(cursor);
        }
        cursor.close();
        close();
        return examSetQuestion;
    }

    public List<ExamSetQuestion> getAllExamSetQuestions() {
        List<ExamSetQuestion> examSetQuestions = new ArrayList<>();
        open();
        Cursor cursor = database.query("ExamSetQuestion", null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                examSetQuestions.add(cursorToExamSetQuestion(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return examSetQuestions;
    }

    public List<ExamSetQuestion> getQuestionsByExamSetId(int examSetId) {
        List<ExamSetQuestion> examSetQuestions = new ArrayList<>();
        open();
        Cursor cursor = database.query("ExamSetQuestion", null, "exam_set_id = ?",
                new String[]{String.valueOf(examSetId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                examSetQuestions.add(cursorToExamSetQuestion(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return examSetQuestions;
    }

    public List<ExamSetQuestion> getExamSetsByQuestionId(int questionId) {
        List<ExamSetQuestion> examSetQuestions = new ArrayList<>();
        open();
        Cursor cursor = database.query("ExamSetQuestion", null, "question_id = ?",
                new String[]{String.valueOf(questionId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                examSetQuestions.add(cursorToExamSetQuestion(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return examSetQuestions;
    }

    public int deleteExamSetQuestion(int questionId, int examSetId) {
        open();
        int rowsAffected = database.delete("ExamSetQuestion",
                "question_id = ? AND exam_set_id = ?",
                new String[]{String.valueOf(questionId), String.valueOf(examSetId)});
        close();
        return rowsAffected;
    }

    private ExamSetQuestion cursorToExamSetQuestion(Cursor cursor) {
        ExamSetQuestion examSetQuestion = new ExamSetQuestion();
        examSetQuestion.setQuestionId(cursor.getInt(cursor.getColumnIndexOrThrow("question_id")));
        examSetQuestion.setExamSetId(cursor.getInt(cursor.getColumnIndexOrThrow("exam_set_id")));
        return examSetQuestion;
    }
}
