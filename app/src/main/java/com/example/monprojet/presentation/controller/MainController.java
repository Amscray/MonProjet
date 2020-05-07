package com.example.monprojet.presentation.controller;

import android.content.SharedPreferences;

import com.example.monprojet.Constants;
import com.example.monprojet.Singletons;
import com.example.monprojet.presentation.model.Pokemon;
import com.example.monprojet.presentation.model.RestPokemonResponse;
import com.example.monprojet.presentation.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainController {

    private SharedPreferences SharedPreferences;
    private Gson gson;
    private MainActivity view;

    public MainController(MainActivity mainActivity ,Gson gson, SharedPreferences SharedPreferences){
        this.view = mainActivity;
        this.gson=gson;
        this.SharedPreferences = SharedPreferences;

    }
    public void onStart(){




        List<Pokemon> pokemonList = getDataFromCache();

        if(pokemonList != null){

            view.showList(pokemonList);
        }else{

            makeApiCall();
        }

    }

    private void makeApiCall(){





        Call<RestPokemonResponse> call = Singletons.getPokeApi().getPokemonResponse();
        call.enqueue(new Callback<RestPokemonResponse>() {
            @Override
            public void onResponse(Call<RestPokemonResponse> call, Response<RestPokemonResponse> response) {
                if(response.isSuccessful() && response.body()!= null){

                    List<Pokemon> pokemonList = response.body().getResults();
                    saveList(pokemonList);

                    view.showList(pokemonList);
                }else {
                    view.showError();
                }
            }


            @Override
            public void onFailure(Call<RestPokemonResponse> call, Throwable t) {
                view.showError();

            }
        });
    }

    private void saveList(List<Pokemon> pokemonList) {
        String jsonString = gson.toJson(pokemonList);
        SharedPreferences

                .edit()

                .putString(Constants.KEY_POKEMON_LIST, jsonString)
                .apply();
        //Toast.makeText(getApplicationContext(), "List saved", Toast.LENGTH_SHORT).show();

    }

    private List<Pokemon> getDataFromCache() {
        String jsonPokemon = SharedPreferences.getString(Constants.KEY_POKEMON_LIST, null);

        if(jsonPokemon == null){
            return null;
        }else{
            Type listType = new TypeToken<List<Pokemon>>(){}.getType();

            return  gson.fromJson(jsonPokemon, listType);
        }


    }

    public void onItemCLick(Pokemon pokemon){

    }

    public void onButtonAClick(){

    }
    public void onButtonBClick(){

    }
}
