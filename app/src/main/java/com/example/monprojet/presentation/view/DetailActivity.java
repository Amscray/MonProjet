package com.example.monprojet.presentation.view;

import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.monprojet.R;
import com.example.monprojet.Singletons;
import com.example.monprojet.presentation.model.Pokemon;


public class DetailActivity extends AppCompatActivity {
    private TextView textDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textDetail = findViewById(R.id.detail_txt);

        Intent intent = getIntent();
        String pokemonJson = intent.getStringExtra("pokemonKey");
        Pokemon pokemon = Singletons.getGson().fromJson(pokemonJson, Pokemon.class);
        showDetail(pokemon);
    }

    private void showDetail(Pokemon pokemon) {
        textDetail.setText(pokemon.getName());
    }


}









