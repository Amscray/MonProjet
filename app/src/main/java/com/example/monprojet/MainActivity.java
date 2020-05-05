package com.example.monprojet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences SharedPreferences;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences = getSharedPreferences("Pokemon calis", Context.MODE_PRIVATE);


         gson = new GsonBuilder()
                .setLenient()
                .create();
            List<Pokemon> pokemonList = getDataFromCache();
         if(pokemonList != null){

            showList(pokemonList);
         }else{

             makeApiCall();
         }


    }

    private List<Pokemon> getDataFromCache() {
        String jsonPokemon = SharedPreferences.getString("jsonPokemonList", null);

                if(jsonPokemon == null){
                    return null;
                }else{
                    Type listType = new TypeToken<List<Pokemon>>(){}.getType();

                    return  gson.fromJson(jsonPokemon, listType);
                }


    }

    private void showList(List<Pokemon> pokemonList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);





        mAdapter = new MyAdapter(pokemonList);
        recyclerView.setAdapter(mAdapter);
    }

    private static final String BASE_URL = "https://pokeapi.co/";

    private void makeApiCall(){



            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            PokeApi pokeApi = retrofit.create(PokeApi.class);

            Call<RestPokemonResponse> call = pokeApi.getPokemonResponse();
           call.enqueue(new Callback<RestPokemonResponse>() {
               @Override
               public void onResponse(Call<RestPokemonResponse> call, Response<RestPokemonResponse> response) {
                   if(response.isSuccessful() && response.body()!= null){

                       List<Pokemon> pokemonList = response.body().getResults();
                       saveList(pokemonList);
                       
                       showList(pokemonList);
                   }else {
                       showError();
                   }
                 }


               @Override
               public void onFailure(Call<RestPokemonResponse> call, Throwable t) {
                   showError();

               }
           });
    }

    private void saveList(List<Pokemon> pokemonList) {
        String jsonString = gson.toJson(pokemonList);
        SharedPreferences

                .edit()

                .putString("jsonPokemonList", jsonString)
                .apply();
        Toast.makeText(getApplicationContext(), "List saved", Toast.LENGTH_SHORT).show();

    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "ApiError", Toast.LENGTH_SHORT).show();
    }
}


