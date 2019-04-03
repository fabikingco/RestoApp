package com.wposs.buc.restpapp.Activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.wposs.buc.restpapp.R;

public class ConfigurarMesasActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText etMesas;
    Switch swDomicilios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_mesas);

        etMesas = findViewById(R.id.etMesas);
        swDomicilios = findViewById(R.id.swDomicilios);

        sharedPreferences = getSharedPreferences("configuracionRestaurante", Context.MODE_PRIVATE);
        String numeroMesas = sharedPreferences.getString("mesas", "0");
        boolean infoDomicilios = sharedPreferences.getBoolean("domicilios", false);

        etMesas.setText(numeroMesas);
        swDomicilios.setChecked(infoDomicilios);


    }


    public void guardarConfiguracion(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mesas", etMesas.getText().toString());
        editor.putBoolean("domicilios", swDomicilios.isChecked());
        editor.apply();

        finish();
    }

    public void cancelar(View view) {
        finish();
    }
}
