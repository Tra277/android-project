package com.example.androidproject.db;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.androidproject.dao.AnswerDAO;
import com.example.androidproject.dao.CategoryDAO;
import com.example.androidproject.dao.DrivingLicenseDAO;
import com.example.androidproject.dao.ExamSetDAO;
import com.example.androidproject.dao.ExamSetQuestionDAO;
import com.example.androidproject.dao.QuestionDAO;
import com.example.androidproject.model.Answer;
import com.example.androidproject.model.Category;
import com.example.androidproject.model.DrivingLicense;
import com.example.androidproject.model.ExamSet;
import com.example.androidproject.model.ExamSetQuestion;
import com.example.androidproject.model.Question;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "QuizApp.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create DrivingLicense table
        db.execSQL("CREATE TABLE DrivingLicense (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "code TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "name TEXT NOT NULL" +
                ")");

        // Create Category table
        db.execSQL("CREATE TABLE Category (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "description TEXT NOT NULL," +
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
                "question_explanation TEXT NOT NULL," +
                "question_status TEXT CHECK(question_status IN ('correct', 'incorrect', 'not_yet_done')) NOT NULL," +
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
                "total_wrong_answer INTEGER NOT NULL," +
                "is_showed INTEGER NOT NULL" +
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
    private int getCategoryIdByName(String name, CategoryDAO categoryDAO) {
        for (Category cat : categoryDAO.getAllCategories()) {
            if (cat.getName().equals(name)) return cat.getId();
        }
        return -1; // Handle error case
    }
    public void populateInitialData() {
        SQLiteDatabase db = this.getWritableDatabase();
        if (DatabaseUtils.queryNumEntries(db, "DrivingLicense") == 0) {
            // DAOs as before
            DrivingLicenseDAO drivingLicenseDAO = new DrivingLicenseDAO(context);
            CategoryDAO categoryDAO = new CategoryDAO(context);
            QuestionDAO questionDAO = new QuestionDAO(context);
            AnswerDAO answerDAO = new AnswerDAO(context);
            ExamSetDAO examSetDAO = new ExamSetDAO(context);
            ExamSetQuestionDAO examSetQuestionDAO = new ExamSetQuestionDAO(context);

            // Insert initial DrivingLicense and Categories (as above)
            long licenseA1Id = drivingLicenseDAO.insertDrivingLicense(new DrivingLicense(/*...*/));
            long trafficRulesId = categoryDAO.insertCategory(new Category(/*...*/));
            long roadSignsId = categoryDAO.insertCategory(new Category(/*...*/));
            long safetyId = categoryDAO.insertCategory(new Category(/*...*/));

            // Bulk questions data (example array)
            String[][] questionsData = {
                    {"What is the speed limit in a residential area?", "50 km/h", "true", "70 km/h", "false", "Traffic Rules", ""},
                    {"What does a red traffic light mean?", "Stop", "true", "Go", "false", "Road Signs", "traffic_light_red"},
                    {"When should you use a helmet?", "Always when riding", "true", "Only on highways", "false", "Safety", ""},
                    {"What does a yellow traffic light mean?", "Prepare to stop", "true", "Speed up", "false", "Road Signs", "traffic_light_yellow"},
                    {"What is the minimum following distance at 60 km/h?", "30 meters", "true", "15 meters", "false", "Traffic Rules", ""}
                    // Add more rows for 325 questions
            };

            ExamSet examSet = new ExamSet("Đề 1",0,0,true);
            long examSetId = examSetDAO.addExamSet(examSet);

            for (String[] data : questionsData) {
                Question question = new Question();
                question.setContent(data[0]);
                question.setImagePath(data[5]); // Image path
                question.setCriticalQuiz(true); // Adjust as needed
                question.setConfusingQuiz(false); // Adjust as needed
                question.setQuestionExplanation("Explanation for " + data[0]);
                question.setQuestionStatus("not_yet_done");
                question.setCategoryId(getCategoryIdByName(data[4], categoryDAO)); // Map category name to ID
                long questionId = questionDAO.insertQuestion(question);

                Answer answer1 = new Answer();
                answer1.setCorrect(Boolean.parseBoolean(data[2]));
                answer1.setContent(data[1]);
                answer1.setImagePath("");
                answer1.setQuestionId((int) questionId);
                answerDAO.insertAnswer(answer1);

                Answer answer2 = new Answer();
                answer2.setCorrect(Boolean.parseBoolean(data[4]));
                answer2.setContent(data[3]);
                answer2.setImagePath("");
                answer2.setQuestionId((int) questionId);
                answerDAO.insertAnswer(answer2);

                ExamSetQuestion eq = new ExamSetQuestion();
                eq.setQuestionId((int) questionId);
                eq.setExamSetId((int) examSetId);
                examSetQuestionDAO.insertExamSetQuestion(eq);
            }
        }
    }
}
