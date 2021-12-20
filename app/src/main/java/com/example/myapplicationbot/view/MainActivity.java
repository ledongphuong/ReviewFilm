package com.example.myapplicationbot.view;

import android.os.Bundle;

import com.example.myapplicationbot.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplicationbot.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nvView);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nvNowplaying, R.id.nvPoppular, R.id.nvToprated, R.id.nvFav)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nvHostFragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.nvView, navController);

    }

}