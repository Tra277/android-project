package com.example.androidproject.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.androidproject.dao.TrafficSignDAO;
import com.example.androidproject.model.TrafficSign;

import java.util.List;

public class SignListViewModel extends AndroidViewModel {
    private TrafficSignDAO trafficSignDAO;
    private MutableLiveData<List<TrafficSign>> _signs = new MutableLiveData<>();
    public LiveData<List<TrafficSign>> signs = _signs;

    public SignListViewModel(@NonNull Application application) {
        super(application);
        trafficSignDAO = new TrafficSignDAO(application);
    }

    public void loadTrafficSignsByCategory(int categoryId) {
        _signs.setValue(trafficSignDAO.getTrafficSignsByCategory(categoryId));
    }
}


