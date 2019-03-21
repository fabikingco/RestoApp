package com.wposs.buc.restpapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ConfigurarRestauranteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_restaurante);
    }

    public void agregarNuevoProducto(View view) {
        Tools.startView(this, NuevoProductoActivity.class);
    }
}
