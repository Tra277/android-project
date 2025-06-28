package com.example.androidproject.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.androidproject.dao.TrafficSignDAO;
import com.example.androidproject.model.TrafficSign;

public class SignDetailViewModel extends AndroidViewModel {
    private TrafficSignDAO trafficSignDAO;
    private MutableLiveData<TrafficSign> _sign = new MutableLiveData<>();
    public LiveData<TrafficSign> sign = _sign;

    public SignDetailViewModel(@NonNull Application application) {
        super(application);
        trafficSignDAO = new TrafficSignDAO(application);
    }

    public void loadTrafficSign(int signId) {
        _sign.setValue(trafficSignDAO.getTrafficSignById(signId));
    }
}


