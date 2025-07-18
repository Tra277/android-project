package com.example.androidproject.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.androidproject.activity.fragment.QuestionFragment;
import com.example.androidproject.dao.QuestionDAO;
import com.example.androidproject.model.Question;

import java.util.List;

public class QuestionPagerAdapter extends FragmentStateAdapter {
    private List<Question> questions;

    private boolean isReviewMode;
    private QuestionDAO questionDAO;

    public QuestionPagerAdapter(FragmentActivity fragmentActivity, List<Question> questions, boolean isReviewMode) {
        super(fragmentActivity);
        this.questions = questions;
        this.isReviewMode = isReviewMode;
        this.questionDAO = new QuestionDAO(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Question question = questions.get(position);
        int selectedAnswerId = questionDAO.getQuestionById(question.getId()).getSelectedAnswerId();
        return QuestionFragment.newInstance(question.getId(), isReviewMode, selectedAnswerId);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
