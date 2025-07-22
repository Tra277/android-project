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
    private static final String[] KEYS = {
            com.example.androidproject.BuildConfig.GEMINI_API_KEY_1,
            com.example.androidproject.BuildConfig.GEMINI_API_KEY_2,
            com.example.androidproject.BuildConfig.GEMINI_API_KEY_3
    };
    private int keyIndex = 0;

    private String nextKey() {
        // cycle through keys; if empty string encountered skip it
        for (int i = 0; i < KEYS.length; i++) {
            String k = KEYS[keyIndex % KEYS.length];
            keyIndex++;
            if (k != null && !k.isEmpty()) return k;
        }
        return ""; // no valid key
    }

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
        String combined = systemPrompt + "\n\n" + question;
        GenerateContentRequest.Part part = new GenerateContentRequest.Part(combined);
        GenerateContentRequest.Content content = new GenerateContentRequest.Content("user", Collections.singletonList(part));
        GenerateContentRequest.GenerationConfig config = new GenerateContentRequest.GenerationConfig(0.2, 160);
        GenerateContentRequest request = new GenerateContentRequest(java.util.Arrays.asList(content), config);

        String apiKey = KEYS[keyIndex % KEYS.length];
        api.generateContent(apiKey, request).enqueue(new Callback<GenerateContentResponse>() {
            @Override
            public void onResponse(Call<GenerateContentResponse> call, Response<GenerateContentResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().candidates != null && !response.body().candidates.isEmpty()) {
                    GenerateContentResponse.Part part = response.body().candidates.get(0).content.parts.get(0);
                    callback.onSuccess(part.text);
                } else if ((response.code()==429 || response.code()==503) && KEYS.length>1) {
                    // rotate key and retry once
                    String newKey = nextKey();
                    if(!newKey.isEmpty()) {
                        api.generateContent(newKey, request).enqueue(this);
                        return;
                    }
                    String errBody=null;
                    try{ if(response.errorBody()!=null) errBody=response.errorBody().string();}catch(Exception ignored){}
                    callback.onError("HTTP "+response.code()+" No answer - "+errBody);
                    Log.e("GeminiService","All keys exhausted.");
                } else {
                    String errBody = null;
                    try { if(response.errorBody()!=null) errBody = response.errorBody().string(); } catch(Exception ignored){}
                    callback.onError("HTTP " + response.code() + " No answer" + (errBody!=null? (" - "+errBody):""));
                    Log.e("GeminiService", "Empty candidates: HTTP " + response.code() + " body=" + errBody);
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