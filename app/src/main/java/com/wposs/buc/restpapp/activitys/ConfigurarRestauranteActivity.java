package com.wposs.buc.restpapp.activitys;

import androidx.appcompat.app.AppCompatActivity;
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
        Tools.startView(this, NuevoProductoActivity.class, false);
    }

    public void editarCategorias(View view) {
        Tools.startView(this, EditarCategoriasActivity.class, false);
    }

    public void visualizarProductos(View view) {
        Tools.startView(this, VisualizarProductosActivity.class, false);
    }

    public void crearMesas(View view) {
        Tools.startView(this, ConfigurarMesasActivity.class, false);
    }

    public void configuracionUsuarios(View view) {
        Tools.startView(this, ConfiguracionUsuariosActivity.class, false);
    }
}
