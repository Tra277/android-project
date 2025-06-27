package com.example.androidproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidproject.db.DatabaseHelper;
import com.example.androidproject.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public CategoryDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    private void open() {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public long insertCategory(Category category) {
        open();
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        values.put("description", category.getDescription());
        values.put("license_id", category.getLicenseId());

        long id = database.insert("Category", null, values);
        close();
        return id;
    }

    public Category getCategoryById(int id) {
        open();
        Cursor cursor = database.query("Category", null, "id = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        Category category = null;
        if (cursor.moveToFirst()) {
            category = cursorToCategory(cursor);
        }
        cursor.close();
        close();
        return category;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        open();
        Cursor cursor = database.query("Category", null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                categories.add(cursorToCategory(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return categories;
    }

    public List<Category> getCategoriesByLicenseId(int licenseId) {
        List<Category> categories = new ArrayList<>();
        open();
        Cursor cursor = database.query("Category", null, "license_id = ?",
                new String[]{String.valueOf(licenseId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                categories.add(cursorToCategory(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return categories;
    }

    public int updateCategory(Category category) {
        open();
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        values.put("description", category.getDescription());
        values.put("license_id", category.getLicenseId());

        int rowsAffected = database.update("Category", values, "id = ?",
                new String[]{String.valueOf(category.getId())});
        close();
        return rowsAffected;
    }

    public void deleteCategory(int id) {
        open();
        database.delete("Category", "id = ?",
                new String[]{String.valueOf(id)});
        close();
    }

    private Category cursorToCategory(Cursor cursor) {
        Category category = new Category();
        category.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        category.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        category.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
        category.setLicenseId(cursor.getInt(cursor.getColumnIndexOrThrow("license_id")));
        return category;
    }

    public List<Category> getCategoriesWithQuestionCountsByLicenseId(int licenseId) {
        List<Category> categories = new ArrayList<>();
        open();
        Cursor cursor = database.query("Category", null, "license_id = ?",
                new String[]{String.valueOf(licenseId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Category category = cursorToCategory(cursor);
                int categoryId = category.getId();

                // Get total questions for this category
                Cursor totalQuestionsCursor = database.rawQuery(
                        "SELECT COUNT(*) FROM Question WHERE category_id = ?",
                        new String[]{String.valueOf(categoryId)}
                );
                if (totalQuestionsCursor.moveToFirst()) {
                    category.setTotalQuestions(totalQuestionsCursor.getInt(0));
                }
                totalQuestionsCursor.close();

                // Get done questions for this category (status 'correct' or 'incorrect')
                Cursor doneQuestionsCursor = database.rawQuery(
                        "SELECT COUNT(*) FROM Question WHERE category_id = ? AND (question_status = 'correct' OR question_status = 'incorrect')",
                        new String[]{String.valueOf(categoryId)}
                );
                if (doneQuestionsCursor.moveToFirst()) {
                    category.setDoneQuestions(doneQuestionsCursor.getInt(0));
                }
                doneQuestionsCursor.close();

                categories.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return categories;
    }
}
