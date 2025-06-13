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

    public QuestionPagerAdapter(FragmentActivity fragmentActivity, List<Question> questions) {
        super(fragmentActivity);
        this.questions = questions;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return QuestionFragment.newInstance(questions.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}