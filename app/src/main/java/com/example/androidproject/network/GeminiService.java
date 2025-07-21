package com.example.androidproject.network;

import android.util.Log;

import com.example.androidproject.BuildConfig;
import com.example.androidproject.network.model.GenerateContentRequest;
import com.example.androidproject.network.model.GenerateContentResponse;

import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeminiService {

    public interface GeminiCallback {
        void onSuccess(String reply);
        void onError(String error);
    }

    private static final String BASE_URL = "https://generativelanguage.googleapis.com/";
    private static GeminiService instance;
    private final GeminiApi api;

    private GeminiService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(GeminiApi.class);
    }

    public static GeminiService getInstance() {
        if (instance == null) {
            instance = new GeminiService();
        }
        return instance;
    }

    public void ask(String question, GeminiCallback callback) {
        //prompt
        String systemPrompt = "You are a Vietnamese driving instructor. Only answer questions about driving, road rules, traffic signs, penalties. If the question is unrelated, politely refuse.";
        GenerateContentRequest.Part systemPart = new GenerateContentRequest.Part(systemPrompt);
        GenerateContentRequest.Part userPart = new GenerateContentRequest.Part(question);

        GenerateContentRequest.Content systemContent = new GenerateContentRequest.Content("user", Collections.singletonList(systemPart));
        GenerateContentRequest.Content userContent = new GenerateContentRequest.Content("user", Collections.singletonList(userPart));

        GenerateContentRequest.GenerationConfig config = new GenerateContentRequest.GenerationConfig(0.25, 100);
        GenerateContentRequest request = new GenerateContentRequest(java.util.Arrays.asList(systemContent, userContent), config);

        api.generateContent(BuildConfig.GEMINI_API_KEY, request).enqueue(new Callback<GenerateContentResponse>() {
            @Override
            public void onResponse(Call<GenerateContentResponse> call, Response<GenerateContentResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().candidates != null && !response.body().candidates.isEmpty()) {
                    GenerateContentResponse.Part part = response.body().candidates.get(0).content.parts.get(0);
                    callback.onSuccess(part.text);
                } else {
                    callback.onError("No answer");
                }
            }

            @Override
            public void onFailure(Call<GenerateContentResponse> call, Throwable t) {
                Log.e("GeminiService", "Error", t);
                callback.onError(t.getMessage());
            }
        });
    }
} 