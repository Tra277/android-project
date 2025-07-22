package com.example.androidproject.network;

import android.util.Log;

import com.example.androidproject.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.ResponseBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HfService {
    public interface HfCallback { void onSuccess(String reply); void onError(String err);}    

    private static final String BASE="https://api-inference.huggingface.co/";
    private static final String MODEL="mistralai/Mistral-7B-Instruct-v0.2";

    private static HfService instance;
    private final HfApi api;

    private HfService() {
        HttpLoggingInterceptor logging=new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original=chain.request();
                    Request.Builder b=original.newBuilder()
                            .header("Authorization","Bearer "+ BuildConfig.HF_TOKEN);
                    return chain.proceed(b.build());
                })
                .addInterceptor(logging)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
        Retrofit r=new Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        api=r.create(HfApi.class);
    }

    public static HfService getInstance(){if(instance==null) instance=new HfService();return instance;}

    public void ask(String prompt, HfCallback cb){
        try {
            JSONObject obj=new JSONObject();
            obj.put("inputs", prompt);
            JSONObject params=new JSONObject();
            params.put("max_new_tokens",160);
            params.put("temperature",0.7);
            obj.put("parameters",params);
            RequestBody body=RequestBody.create(obj.toString().getBytes(StandardCharsets.UTF_8));
            api.generate(MODEL,body).enqueue(new Callback<ResponseBody>() {
                @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful() && response.body()!=null){
                        try {
                            String json=response.body().string();
                            String text=json;
                            // try parse array
                            if(json.trim().startsWith("[")){
                               JSONArray arr=new JSONArray(json);
                               if(arr.length()>0) text=arr.getJSONObject(0).getString("generated_text");
                            } else {
                               JSONObject o=new JSONObject(json);
                               if(o.has("generated_text")) text=o.getString("generated_text");
                            }
                            cb.onSuccess(text);
                        } catch(Exception e){ cb.onError(e.toString());}
                    } else {
                        cb.onError("HF error "+response.code());
                    }
                }
                @Override public void onFailure(Call<ResponseBody> call, Throwable t){cb.onError(t.toString());}
            });
        }catch(JSONException e){cb.onError(e.toString());}
    }
} 