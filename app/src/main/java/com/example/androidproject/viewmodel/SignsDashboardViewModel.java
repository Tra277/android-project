package com.example.androidproject.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.androidproject.dao.TrafficSignCategoryDAO;
import com.example.androidproject.model.TrafficSignCategory;

import java.util.List;

public class SignsDashboardViewModel extends AndroidViewModel {
    private TrafficSignCategoryDAO trafficSignCategoryDAO;
    private MutableLiveData<List<TrafficSignCategory>> _categories = new MutableLiveData<>();
    public LiveData<List<TrafficSignCategory>> categories = _categories;

    public SignsDashboardViewModel(@NonNull Application application) {
        super(application);
        trafficSignCategoryDAO = new TrafficSignCategoryDAO(application);
        loadTrafficSignCategories();
    }

    public void loadTrafficSignCategories() {
        _categories.setValue(trafficSignCategoryDAO.getAllTrafficSignCategories());
    }
}


