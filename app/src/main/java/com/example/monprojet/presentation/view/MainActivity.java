package com.example.monprojet.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.monprojet.Constants;
import com.example.monprojet.R;
import com.example.monprojet.presentation.controller.MainController;
import com.example.monprojet.presentation.model.Pokemon;
import com.example.monprojet.presentation.model.RestPokemonResponse;
import com.example.monprojet.data.PokeApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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


    private MainController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new MainController(
                this,

                new GsonBuilder()
                        .setLenient()
                        .create(),
                getSharedPreferences("Pokemon calis", Context.MODE_PRIVATE)

        );
        controller.onStart();



    }



    public void showList(List<Pokemon> pokemonList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);





        mAdapter = new MyAdapter(pokemonList);
        recyclerView.setAdapter(mAdapter);
    }





    public void showError() {
        Toast.makeText(getApplicationContext(), "ApiError", Toast.LENGTH_SHORT).show();
    }
}


