package com.example.monprojet.data;

import com.example.monprojet.presentation.model.RestPokemonResponse;

public interface PokeCallback {

    public  void onSucess(RestPokemonResponse response);
    public  void onFailure();
}
