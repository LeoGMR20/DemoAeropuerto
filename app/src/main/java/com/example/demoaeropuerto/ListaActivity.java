package com.example.demoaeropuerto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.demoaeropuerto.databinding.ActivityListaBinding;

public class ListaActivity extends AppCompatActivity {

    private ActivityListaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}