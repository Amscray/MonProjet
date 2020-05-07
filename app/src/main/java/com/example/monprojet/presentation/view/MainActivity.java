package com.example.monprojet.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.monprojet.R;
import com.example.monprojet.Singletons;
import com.example.monprojet.presentation.controller.MainController;
import com.example.monprojet.presentation.model.Pokemon;

import java.util.List;

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

                Singletons.getGson(),
                Singletons.getSharedPreferences(getApplicationContext())

        );
        controller.onStart();



    }



    public void showList(List<Pokemon> pokemonList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);





        mAdapter = new MyAdapter(pokemonList, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pokemon item) {
                controller.onItemCLick(item);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }





    public void showError() {
        Toast.makeText(getApplicationContext(), "ApiError", Toast.LENGTH_SHORT).show();
    }

    public void navigateToDetails(Pokemon pokemon) {
        Toast.makeText(getApplicationContext(), "Pas de navigation", Toast.LENGTH_SHORT).show();
    }
}


