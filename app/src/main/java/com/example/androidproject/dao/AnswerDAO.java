package com.example.androidproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidproject.db.DatabaseHelper;
import com.example.androidproject.model.Answer;

import java.util.ArrayList;
import java.util.List;

public class AnswerDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public AnswerDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    private void open() {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public long insertAnswer(Answer answer) {
        open();
        ContentValues values = new ContentValues();
        values.put("is_correct", answer.isCorrect() ? 1 : 0);
        values.put("content", answer.getContent());
        values.put("image_path", answer.getImagePath());
        values.put("question_id", answer.getQuestionId());

        long id = database.insert("Answer", null, values);
        close();
        return id;
    }

    public Answer getAnswerById(int id) {
        open();
        Cursor cursor = database.query("Answer", null, "id = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        Answer answer = null;
        if (cursor.moveToFirst()) {
            answer = cursorToAnswer(cursor);
        }
        cursor.close();
        close();
        return answer;
    }

    public List<Answer> getAllAnswers() {
        List<Answer> answers = new ArrayList<>();
        open();
        Cursor cursor = database.query("Answer", null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                answers.add(cursorToAnswer(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return answers;
    }

    public List<Answer> getAnswersByQuestionId(int questionId) {
        List<Answer> answers = new ArrayList<>();
        open();
        Cursor cursor = database.query("Answer", null, "question_id = ?",
                new String[]{String.valueOf(questionId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                answers.add(cursorToAnswer(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return answers;
    }

    public int updateAnswer(Answer answer) {
        open();
        ContentValues values = new ContentValues();
        values.put("is_correct", answer.isCorrect() ? 1 : 0);
        values.put("content", answer.getContent());
        values.put("image_path", answer.getImagePath());
        values.put("question_id", answer.getQuestionId());

        int rowsAffected = database.update("Answer", values, "id = ?",
                new String[]{String.valueOf(answer.getId())});
        close();
        return rowsAffected;
    }

    public void deleteAnswer(int id) {
        open();
        database.delete("Answer", "id = ?",
                new String[]{String.valueOf(id)});
        close();
    }

    private Answer cursorToAnswer(Cursor cursor) {
        Answer answer = new Answer();
        answer.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        answer.setCorrect(cursor.getInt(cursor.getColumnIndexOrThrow("is_correct")) == 1);
        answer.setContent(cursor.getString(cursor.getColumnIndexOrThrow("content")));
        answer.setImagePath(cursor.getString(cursor.getColumnIndexOrThrow("image_path")));
        answer.setQuestionId(cursor.getInt(cursor.getColumnIndexOrThrow("question_id")));
        return answer;
    }
}
