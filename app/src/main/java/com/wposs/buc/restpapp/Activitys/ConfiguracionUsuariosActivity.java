package com.wposs.buc.restpapp.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.Tools;

public class ConfiguracionUsuariosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_usuarios);


    }

    public void crearUsuarios(View view) {
        Tools.startView(this, CrearUsuarioActivity.class);
    }

    public void visualizarUsuarios(View view) {

    }
}
