package com.example.androidproject.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HfApi {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("models/{model}")
    Call<ResponseBody> generate(@Path("model") String model, @Body okhttp3.RequestBody body);
} 