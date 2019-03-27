package com.wposs.buc.restpapp.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wposs.buc.restpapp.BD.Controler.ClsConexion;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.Tools;

public class MainActivity extends AppCompatActivity {

    ClsConexion bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bd = new ClsConexion(this);
    }

    public void crearPedido(View view) {
        Toast.makeText(this, "Opcion no disponible", Toast.LENGTH_SHORT).show();
    }

    public void finalizarPedido(View view) {
        Toast.makeText(this, "Opcion no disponible", Toast.LENGTH_SHORT).show();
    }

    public void configurarRestaurante(View view) {
        Tools.startView(this, ConfigurarRestauranteActivity.class);
    }
}
