package com.wposs.buc.restpapp.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.Tools;

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
