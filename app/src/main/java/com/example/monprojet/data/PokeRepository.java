package com.example.monprojet.data;

import android.content.SharedPreferences;

import com.example.monprojet.presentation.model.RestPokemonResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokeRepository {

    private PokeApi pokeApi;
    private SharedPreferences SharedPreferences;

    public PokeRepository(android.content.SharedPreferences sharedPreferences) {
        SharedPreferences = sharedPreferences;
    }

    public PokeRepository(PokeApi pokeApi) {
        this.pokeApi = pokeApi;
    }

        public void getPokemonResponse (final PokeCallback callback){

            pokeApi.getPokemonResponse().enqueue((new Callback<RestPokemonResponse>() {
                @Override
                public void onResponse(Call<RestPokemonResponse> call, Response<RestPokemonResponse> response) {
                    if (response.isSuccessful()&& response.body()!=null){
                        callback.onSucess(response.body());

                    }else {
                        callback.onFailure();
                    }
                }

                @Override
                public void onFailure(Call<RestPokemonResponse> call, Throwable t) {
                        callback.onFailure();
                }
            }));
        }
}
