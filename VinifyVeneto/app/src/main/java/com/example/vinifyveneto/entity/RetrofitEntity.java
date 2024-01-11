package com.example.vinifyveneto.entity;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitEntity {

    private static String BASE_URL2="http://172.17.0.1:9000";

    private static String BASE_URL = "http://157.138.164.224:9000";

    private static String BASE_URL3 = "http://192.168.209.123:9000";

    public static RetrofitInterface getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        return retrofitInterface;
    }
}
