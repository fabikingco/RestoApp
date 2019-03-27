package com.wposs.buc.restpapp.Activitys;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.wposs.buc.restpapp.BD.Controler.ClsConexion;
import com.wposs.buc.restpapp.R;

import java.util.ArrayList;

public class EditarCategoriasActivity extends AppCompatActivity {

    ClsConexion bd;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_categorias);
        bd = new ClsConexion(this);
        crearBotones();
    }

    private void crearBotones() {


        ArrayList<String> categorias = bd.getAllCategorias();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categorias);

        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String categoria = adapter.getItem(position);
                Toast.makeText(EditarCategoriasActivity.this, "Data seleccionada " + categoria, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
