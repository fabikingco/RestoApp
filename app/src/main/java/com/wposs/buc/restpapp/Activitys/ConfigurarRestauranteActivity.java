package com.wposs.buc.restpapp.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

    public void editarCategorias(View view) {
        Tools.startView(this, EditarCategoriasActivity.class);
    }

    public void visualizarProductos(View view) {
        Tools.startView(this, VisualizarProductosActivity.class);
    }

    public void crearMesas(View view) {
        Tools.startView(this, ConfigurarMesasActivity.class);
    }

    public void configuracionUsuarios(View view) {

    }
}
