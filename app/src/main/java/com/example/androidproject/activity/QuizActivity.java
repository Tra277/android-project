
package com.example.androidproject.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.androidproject.OnAnswerSubmittedListener;
import com.example.androidproject.R;
import com.example.androidproject.activity.fragment.QuestionBottomSheetFragment;
import com.example.androidproject.adapter.QuestionPagerAdapter;
import com.example.androidproject.dao.DrivingLicenseDAO;
import com.example.androidproject.dao.ExamSetDAO;
import com.example.androidproject.dao.ExamSetQuestionDAO;
import com.example.androidproject.dao.QuestionDAO;
import com.example.androidproject.model.DrivingLicense;
import com.example.androidproject.model.ExamSet;
import com.example.androidproject.model.ExamSetQuestion;
import com.example.androidproject.model.Question;
import com.example.androidproject.utils.LicenseInfoPopup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuizActivity extends BaseActivity implements OnAnswerSubmittedListener, QuestionBottomSheetFragment.OnQuestionSelectedListener {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private TextView tvTimer;
    private ProgressBar progressBar;
    private QuestionDAO questionDAO;
    private List<Question> questions;
    private CountDownTimer countDownTimer;
    private int totalQuestions;
    private int completedQuestions;
    private Button btnSubmitQuiz;
    private ExamSetDAO examSetDAO;
    private ExamSetQuestionDAO examSetQuestionDAO;
    private long examSetId;
    private String license_code;
    private int licenseId;
    private DrivingLicenseDAO drivingLicenseDAO ;
    //Default for A1
    private int examTotalQuestions = 25;
    private int totalTimeMinutes = 19;
    private FloatingActionButton fabInfo;
    private LicenseInfoPopup licenseInfoPopup; // Declare LicenseInfoPopup as a member variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Bài thi");
        boolean isReviewMode = getIntent().getBooleanExtra("is_review_mode", false);
        fabInfo = findViewById(R.id.fab_info);
        licenseInfoPopup = new LicenseInfoPopup(this); // Initialize the popup here

        if (isReviewMode) {
            toolbar.setNavigationIcon(R.drawable.ic_exit); // Set back arrow icon
            toolbar.setNavigationOnClickListener(v -> onBackPressed()); // Handle back button click
            fabInfo.setVisibility(View.GONE); // Hide fabInfo in review mode
        } else {
            toolbar.setNavigationIcon(null); // No back arrow in quiz mode
            fabInfo.setVisibility(View.VISIBLE); // Ensure fabInfo is visible in quiz mode
        }
        toolbar.inflateMenu(R.menu.top_app_bar_exam);
        // Initialize views
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        tvTimer = findViewById(R.id.tv_timer);
        progressBar = findViewById(R.id.progress_bar);
        questionDAO = new QuestionDAO(this);
        btnSubmitQuiz = findViewById(R.id.btn_submit_quiz);
        examSetDAO = new ExamSetDAO(this);
        examSetQuestionDAO = new ExamSetQuestionDAO(this);
        examSetDAO.deleteOldExamSets();
        drivingLicenseDAO = new DrivingLicenseDAO(this);
        Intent intent = getIntent();
        int initialPosition = intent.getIntExtra("question_position", -1);
        List<Question> receivedQuestions = intent.getParcelableArrayListExtra("questions");
        SharedPreferences prefs = getSharedPreferences("LicensePrefs", MODE_PRIVATE);
        license_code = prefs.getString("selectedLicenseCode", "A1");
        DrivingLicense license = drivingLicenseDAO.getDrivingLicenseByCode(license_code);
        if(license_code.equals("A1") || license_code.equals("A")) {
            examTotalQuestions = 25;
            totalTimeMinutes = 19;
        } else if( license_code.equals("B")) {
            totalTimeMinutes = 27;
            examTotalQuestions = 30;
        } else if( license_code.equals("C1")){
            examTotalQuestions = 35;
            totalTimeMinutes = 22;
        } else if( license_code.equals("C")){
            examTotalQuestions = 40;
            totalTimeMinutes = 22;
        }else{
            examTotalQuestions = 45;
            totalTimeMinutes = 25;
        }
        if (receivedQuestions != null && !receivedQuestions.isEmpty()) {
            questions = receivedQuestions;
            if (isReviewMode) {
                // In review mode, ensure selectedAnswerId is loaded for each question
                for (int i = 0; i < questions.size(); i++) {
                    Question originalQuestion = questions.get(i);
                    Question updatedQuestion = questionDAO.getQuestionById(originalQuestion.getId());
                    if (updatedQuestion != null) {
                        questions.set(i, updatedQuestion); // Update the question object in the list
                    }
                }
            }
        } else {
            String quizMode = intent.getStringExtra("quiz_mode");
            licenseId = license.getId();
            if (quizMode == null) {
                quizMode = "random_exam"; // Default mode
            }
            if(quizMode.equals("exam_set")) {
                examSetId = intent.getIntExtra("examSetId",1);
                questions = questionDAO.getQuestionsByExamSetId(examSetId);
            }
            if(quizMode.equals("random_exam")) {
                questions = questionDAO.getRandomQuestions(license.getId(),examTotalQuestions);
                ExamSet examSet = new ExamSet("Random Exam", questions.size(), 0, false ,licenseId,false);

                long newExamSetId = examSetDAO.addExamSet(examSet);
                examSetId = newExamSetId;
                for (Question question : questions) {
                    ExamSetQuestion examSetQuestion = new ExamSetQuestion();
                    examSetQuestion.setQuestionId(question.getId());
                    examSetQuestion.setExamSetId((int)newExamSetId);
                    examSetQuestionDAO.insertExamSetQuestion(examSetQuestion);
                }
            }
            if(quizMode.equals("top_wquiz")) {
                fabInfo.setVisibility(View.GONE);
                questions = questionDAO.getConfusingQuestions(license.getId());
                ExamSet examSet = new ExamSet("Confusing Exam", questions.size(), 0, false ,licenseId,false);

                long newExamSetId = examSetDAO.addExamSet(examSet);
                examSetId = newExamSetId;
                for (Question question : questions) {
                    ExamSetQuestion examSetQuestion = new ExamSetQuestion();
                    examSetQuestion.setQuestionId(question.getId());
                    examSetQuestion.setExamSetId((int)newExamSetId);
                    examSetQuestionDAO.insertExamSetQuestion(examSetQuestion);
                }
            }
            if(quizMode.equals("critical_quiz")) {
                fabInfo.setVisibility(View.GONE);
               questions = questionDAO.getCriticalQuestions(license.getId());
                ExamSet examSet = new ExamSet("Critical Exam", questions.size(), 0, false ,licenseId,false);

                long newExamSetId = examSetDAO.addExamSet(examSet);
                examSetId = newExamSetId;
                for (Question question : questions) {
                    ExamSetQuestion examSetQuestion = new ExamSetQuestion();
                    examSetQuestion.setQuestionId(question.getId());
                    examSetQuestion.setExamSetId((int)newExamSetId);
                    examSetQuestionDAO.insertExamSetQuestion(examSetQuestion);
                }
            }
            if(quizMode.equals("wquiz_review")) {
                fabInfo.setVisibility(View.GONE);
                questions = questionDAO.getQuestionsByStatus("incorrect", license.getId());
                if(questions.size() > 0){
                    ExamSet examSet = new ExamSet("Wrong Quiz Review Exam", questions.size(), 0, false ,licenseId,false);
                    long newExamSetId = examSetDAO.addExamSet(examSet);
                    examSetId = newExamSetId;
                    for (Question question : questions) {
                        ExamSetQuestion examSetQuestion = new ExamSetQuestion();
                        examSetQuestion.setQuestionId(question.getId());
                        examSetQuestion.setExamSetId((int)newExamSetId);
                        examSetQuestionDAO.insertExamSetQuestion(examSetQuestion);
                    }
                }
            }
            if(quizMode.equals("category")) {
                fabInfo.setVisibility(View.GONE);
                int categoryId = intent.getIntExtra("categoryId",1);
                questions = questionDAO.getQuestionsByCategory(categoryId);
                ExamSet examSet = new ExamSet("Category Exam", questions.size(), 0, false ,licenseId,false);
                long newExamSetId = examSetDAO.addExamSet(examSet);
                examSetId = newExamSetId;
                for (Question question : questions) {
                    ExamSetQuestion examSetQuestion = new ExamSetQuestion();
                    examSetQuestion.setQuestionId(question.getId());
                    examSetQuestion.setExamSetId((int)newExamSetId);
                    examSetQuestionDAO.insertExamSetQuestion(examSetQuestion);
                }
            }
        }


        // Initialize DAO and load questions
        for (Question question : questions) {
            question.setQuestionStatus("not_yet_done");
            questionDAO.updateQuestion(question);
        }

        if(questions.size() > 0){
            viewPager.setOffscreenPageLimit(questions.size());
        }

        totalQuestions = questions.size();
        completedQuestions = (int) questions.stream()
                .filter(q -> !q.getQuestionStatus().equals("not_yet_done"))
                .count();


        // Set up ViewPager and TabLayout
        QuestionPagerAdapter adapter = new QuestionPagerAdapter(this, questions, isReviewMode);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("Câu " + (position + 1)))
                .attach();
        updateProgress();

        if (initialPosition != -1 && initialPosition < questions.size()) {
            viewPager.setCurrentItem(initialPosition, false); // Set to the specific question
        }

        if (isReviewMode) {
            tvTimer.setVisibility(View.GONE);
            btnSubmitQuiz.setVisibility(View.GONE);
        } else {
            countDownTimer = new CountDownTimer(totalTimeMinutes * 60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int minutes = (int) (millisUntilFinished / 1000) / 60;
                    int seconds = (int) (millisUntilFinished / 1000) % 60;
                    tvTimer.setText(String.format("Time: %02d:%02d", minutes, seconds));
                }

                @Override
                public void onFinish() {
                    tvTimer.setText("Time's up!");
                    // Handle quiz completion
                }
            }.start();
            btnSubmitQuiz.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Xác nhận nộp!")
                        .setMessage("Bạn muốn nộp bài chứ?")
                        .setPositiveButton("Vâng!", (dialog, which) -> {
                            Toast.makeText(this, "Đã nộp bài!", Toast.LENGTH_SHORT).show();
                            submitQuiz();
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            });
        }

        // drawer
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.show_question) {
                Toast.makeText(this, "Show question", Toast.LENGTH_SHORT).show();
                QuestionBottomSheetFragment bottomSheet = QuestionBottomSheetFragment.newInstance(new ArrayList<>(questions));
                bottomSheet.show(getSupportFragmentManager(), "QuestionBottomSheet");
                return true;
            }
            return false;
        });

        fabInfo.setOnClickListener(v -> {
            showLicenseInfoPopup(license);
        });
    }

    private void submitQuiz() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (licenseInfoPopup != null) {
            licenseInfoPopup.dismiss(); // Dismiss the popup when quiz is submitted
        }
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("total_questions", totalQuestions);
        intent.putExtra("exam_set_id", examSetId);

        // Calculate quiz results
        int correctAnswers = 0;
        for (Question question : questions) {
            // Reload the latest status from DB
            Question updated = questionDAO.getQuestionById(question.getId());
            if (updated != null && updated.getQuestionStatus().equals("correct")) {
                correctAnswers++;
            }
        }
        int incorrectAnswers = totalQuestions - correctAnswers;

        // Pass quiz results to ResultActivity
        intent.putExtra("correct_answers", correctAnswers);
        intent.putExtra("incorrect_answers", incorrectAnswers);
        intent.putExtra("time_taken", tvTimer.getText().toString()); // Assuming tvTimer displays the time
        // Create a list of question statuses ("correct", "incorrect", "not_yet_done")
        List<String> questionStatuses = new ArrayList<>();
        for (Question question : questions) {
            // Reload the latest status from DB
            Question updated = questionDAO.getQuestionById(question.getId());
            questionStatuses.add(updated.getQuestionStatus());
        }
        intent.putStringArrayListExtra("question_statuses", new ArrayList<>(questionStatuses));
        intent.putParcelableArrayListExtra("questions", new ArrayList<>(questions));

        startActivity(intent);
    }
    @Override
    public void onAnswerSubmitted(int questionId, String status) {
        // Update the question status in the database
        Question question = questionDAO.getQuestionById(questionId);
        if (question != null) {
            question.setQuestionStatus(status);
            questionDAO.updateQuestion(question);

            // Update completedQuestions count
            completedQuestions = (int) questions.stream()
                    .filter(q -> !q.getQuestionStatus().equals("not_yet_done"))
                    .count();

            // Update progress
            updateProgress();

            // Refresh TabLayout to show updated status
            //refreshTabs();
        }
    }

    public void updateProgress() {
        completedQuestions = 0;

        for (Question question : questions) {
            // Reload the latest status from DB
            Question updated = questionDAO.getQuestionById(question.getId());
            if (updated != null && !updated.getQuestionStatus().equals("not_yet_done")) {
                completedQuestions++;
            }
        }
        int progress = (int) ((completedQuestions / (float) totalQuestions) * 100);
        progressBar.setProgress(progress);
    }

    private void refreshTabs() {
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("Câu " + (position + 1) ));
        mediator.attach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (licenseInfoPopup != null) {
            licenseInfoPopup.dismiss(); // Dismiss the popup to prevent window leaks
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_exam, menu);
        return true;
    }

    @Override
    public void onQuestionSelected(int position) {
        viewPager.setCurrentItem(position, true); // Smooth scroll to the selected question
    }

    private void showLicenseInfoPopup(DrivingLicense license) {
        licenseInfoPopup.show(license); // Use the member variable
    }
}
