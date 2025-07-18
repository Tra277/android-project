package com.example.androidproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidproject.db.DatabaseHelper;
import com.example.androidproject.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public QuestionDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }
    // Open database connection
    private void open() {
        database = dbHelper.getWritableDatabase();
    }

    // Close database connection
    private void close() {
        dbHelper.close();
    }

    // Insert a new question
    public long insertQuestion(Question question) {
        open();
        ContentValues values = new ContentValues();
        values.put("content", question.getContent());
        values.put("image_path", question.getImagePath());
        values.put("is_critical_quiz", question.isCriticalQuiz() ? 1 : 0);
        values.put("is_confusing_quiz", question.isConfusingQuiz() ? 1 : 0);
        values.put("question_explanation", question.getQuestionExplanation());
        values.put("question_status", question.getQuestionStatus());
        values.put("category_id", question.getCategoryId());
        values.put("selected_answer_id", question.getSelectedAnswerId());

        long id = database.insert("Question", null, values);
        close();
        return id;
    }

    // Get a question by ID
    public Question getQuestionById(int id) {
        open();
        Cursor cursor = database.query("Question", null, "id = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        Question question = null;
        if (cursor.moveToFirst()) {
            question = cursorToQuestion(cursor);
        }
        cursor.close();
        close();
        return question;
    }

    // Get all questions
    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        open();
        Cursor cursor = database.query("Question", null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                questions.add(cursorToQuestion(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return questions;
    }

    public List<Question> getQuestionsByExamSetId(long examSetId) {
        List<Question> questions = new ArrayList<>();
        open();
        Cursor cursor = database.rawQuery(
                "SELECT q.* FROM Question q INNER JOIN ExamSetQuestion esq ON q.id = esq.question_id WHERE esq.exam_set_id = ?",
                new String[]{String.valueOf(examSetId)});

        if (cursor.moveToFirst()) {
            do {
                questions.add(cursorToQuestion(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return questions;
    }

    //get random 25 questions
    public List<Question> getRandomQuestions() {
        List<Question> questions = new ArrayList<>();
        open();
        String query = "SELECT * FROM Question ORDER BY RANDOM() LIMIT 25";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                questions.add(cursorToQuestion(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return questions;
    }

    //get random 25 questions by license ID
    public List<Question> getRandomQuestions(int licenseId) {
        List<Question> questions = new ArrayList<>();
        open();
        String query = "SELECT q.* FROM Question q " +
                "INNER JOIN Category c ON q.category_id = c.id " +
                "INNER JOIN DrivingLicense dl ON c.license_id = dl.id " +
                "WHERE dl.id = ? " +
                "ORDER BY RANDOM() LIMIT 25";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(licenseId)});
        if (cursor.moveToFirst()) {
            do {
                questions.add(cursorToQuestion(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return questions;
    }

    public List<Question> getQuestionsByCategory(int categoryId) {
        List<Question> questions = new ArrayList<>();
        open();
        Cursor cursor = database.query("Question", null, "category_id = ?",
                new String[]{String.valueOf(categoryId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                questions.add(cursorToQuestion(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return questions;
    }

    public int updateQuestionSelectedAnswerId(int questionId, int selectedAnswerId) {
        open();
        ContentValues values = new ContentValues();
        values.put("selected_answer_id", selectedAnswerId);
        int rowsAffected = database.update("Question", values, "id = ?", new String[]{String.valueOf(questionId)});
        close();
        return rowsAffected;
    }

    public List<Question> getQuestionsByCategoryAndStatus(int categoryId, String status) {
        List<Question> questions = new ArrayList<>();
        open();
        Cursor cursor = database.query("Question", null, "category_id = ? AND question_status = ?",
                new String[]{String.valueOf(categoryId), status}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                questions.add(cursorToQuestion(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return questions;
    }

    public List<Question> getQuestionsByStatus(String status) {
        List<Question> questions = new ArrayList<>();
        open();
        Cursor cursor = database.query("Question", null, "question_status = ?",
                new String[]{status}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                questions.add(cursorToQuestion(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return questions;
    }

    public List<Question> getQuestionsByStatus(String status, int licenseId) {
        List<Question> questions = new ArrayList<>();
        open();
        String query = "SELECT q.* FROM Question q " +
                "INNER JOIN Category c ON q.category_id = c.id " +
                "INNER JOIN DrivingLicense dl ON c.license_id = dl.id " +
                "WHERE q.question_status = ? AND dl.id = ?";
        Cursor cursor = database.rawQuery(query, new String[]{status, String.valueOf(licenseId)});

        if (cursor.moveToFirst()) {
            do {
                questions.add(cursorToQuestion(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return questions;
    }

    public List<Question> getCriticalQuestions() {
        List<Question> questions = new ArrayList<>();
        open();
        Cursor cursor = database.query("Question", null, "is_critical_quiz = 1",
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                questions.add(cursorToQuestion(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return questions;
    }

    public List<Question> getCriticalQuestions(int licenseId) {
        List<Question> questions = new ArrayList<>();
        open();
        String query = "SELECT q.* FROM Question q " +
                "INNER JOIN Category c ON q.category_id = c.id " +
                "INNER JOIN DrivingLicense dl ON c.license_id = dl.id " +
                "WHERE q.is_critical_quiz = 1 AND dl.id = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(licenseId)});

        if (cursor.moveToFirst()) {
            do {
                questions.add(cursorToQuestion(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return questions;
    }

    public List<Question> getConfusingQuestions() {
        List<Question> questions = new ArrayList<>();
        open();
        Cursor cursor = database.query("Question", null, "is_confusing_quiz = 1",
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                questions.add(cursorToQuestion(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return questions;
    }

    public List<Question> getConfusingQuestions(int licenseId) {
        List<Question> questions = new ArrayList<>();
        open();
        String query = "SELECT q.* FROM Question q " +
                "INNER JOIN Category c ON q.category_id = c.id " +
                "INNER JOIN DrivingLicense dl ON c.license_id = dl.id " +
                "WHERE q.is_confusing_quiz = 1 AND dl.id = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(licenseId)});

        if (cursor.moveToFirst()) {
            do {
                questions.add(cursorToQuestion(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return questions;
    }

    // Update a question
    public int updateQuestion(Question question) {
        open();
        ContentValues values = new ContentValues();
        values.put("content", question.getContent());
        values.put("image_path", question.getImagePath());
        values.put("is_critical_quiz", question.isCriticalQuiz() ? 1 : 0);
        values.put("is_confusing_quiz", question.isConfusingQuiz() ? 1 : 0);
        values.put("question_explanation", question.getQuestionExplanation());
        values.put("question_status", question.getQuestionStatus());
        values.put("category_id", question.getCategoryId());
        values.put("selected_answer_id", question.getSelectedAnswerId());

        int rowsAffected = database.update("Question", values, "id = ?",
                new String[]{String.valueOf(question.getId())});
        close();
        return rowsAffected;
    }

    // Delete a question
    public void deleteQuestion(int id) {
        open();
        database.delete("Question", "id = ?",
                new String[]{String.valueOf(id)});
        close();
    }

    // Helper method to convert Cursor to Question object
    private Question cursorToQuestion(Cursor cursor) {
        Question question = new Question();
        question.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        question.setContent(cursor.getString(cursor.getColumnIndexOrThrow("content")));
        question.setImagePath(cursor.getString(cursor.getColumnIndexOrThrow("image_path")));
        question.setCriticalQuiz(cursor.getInt(cursor.getColumnIndexOrThrow("is_critical_quiz")) == 1);
        question.setConfusingQuiz(cursor.getInt(cursor.getColumnIndexOrThrow("is_confusing_quiz")) == 1);
        question.setQuestionExplanation(cursor.getString(cursor.getColumnIndexOrThrow("question_explanation")));
        question.setQuestionStatus(cursor.getString(cursor.getColumnIndexOrThrow("question_status")));
        question.setCategoryId(cursor.getInt(cursor.getColumnIndexOrThrow("category_id")));
        question.setSelectedAnswerId(cursor.getInt(cursor.getColumnIndexOrThrow("selected_answer_id")));
        return question;
    }
}
