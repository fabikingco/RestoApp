package com.wposs.buc.restpapp.Activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.wposs.buc.restpapp.BD.Controler.ClsConexion;
import com.wposs.buc.restpapp.R;

import java.util.ArrayList;

public class ConfigurarMesasActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText etMesas;
    Switch swDomicilios;
    ClsConexion bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_mesas);
        bd = new ClsConexion(this);

        etMesas = findViewById(R.id.etMesas);
        swDomicilios = findViewById(R.id.swDomicilios);
        updateDatosDeMesas();
    }

    public void updateDatosDeMesas () {
        sharedPreferences = getSharedPreferences("configuracionRestaurante", Context.MODE_PRIVATE);
        boolean infoDomicilios = sharedPreferences.getBoolean("domicilios", false);

        ArrayList<String> mesas = bd.getAllMesasName();
        etMesas.setText(String.valueOf(mesas.size()));

        swDomicilios.setChecked(infoDomicilios);
    }


    public void guardarConfiguracion(View view) {
        String numeroDeMesas = etMesas.getText().toString();
        if (numeroDeMesas.equals("0") || numeroDeMesas.equals("00") || etMesas.getText().toString().isEmpty()){
            Toast.makeText(this, "Digita un numero de mesas superior a 1", Toast.LENGTH_SHORT).show();
            updateDatosDeMesas();
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("domicilios", swDomicilios.isChecked());
        editor.apply();
        finish();
    }

    public void cancelar(View view) {
        finish();
    }
}
