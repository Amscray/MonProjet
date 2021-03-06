package com.example.monprojet;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.monprojet.data.PokeApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {

    private static Gson gsonInstance;
    private static PokeApi pokeApiInstance;
    private static SharedPreferences SharedPreferencesInstance;

    public static Gson getGson(){
        if (gsonInstance==null){
            gsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return gsonInstance;
    }

    public static PokeApi getPokeApi(){
        if(pokeApiInstance== null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();

            pokeApiInstance = retrofit.create(PokeApi.class);

        }
        return pokeApiInstance;
    }

    public static SharedPreferences getSharedPreferences(Context context){
        if (SharedPreferencesInstance==null){
            SharedPreferencesInstance = context.getSharedPreferences("Pokemon calis", Context.MODE_PRIVATE);

        }
        return SharedPreferencesInstance;
    }

}
