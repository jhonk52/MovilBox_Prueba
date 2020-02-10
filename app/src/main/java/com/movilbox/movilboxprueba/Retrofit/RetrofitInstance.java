package com.movilbox.movilboxprueba.Retrofit;

import com.google.gson.GsonBuilder;
import com.movilbox.movilboxprueba.models.User;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    public static Retrofit getInstance(boolean custom_deserializer){

        final String URL = "https://jsonplaceholder.typicode.com/";
        Retrofit retrofit = null;

        if (custom_deserializer) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(User.class, new gsonDeserializer());

                retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                        .build();
            }else{
                retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }

        return retrofit;
    }

}
