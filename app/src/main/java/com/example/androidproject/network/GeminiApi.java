package com.example.androidproject.network;

import com.example.androidproject.network.model.GenerateContentRequest;
import com.example.androidproject.network.model.GenerateContentResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GeminiApi {
    @POST("v1beta/models/gemini-1.5-flash-latest:generateContent")
    Call<GenerateContentResponse> generateContent(@Query("key") String apiKey, @Body GenerateContentRequest request);
} 