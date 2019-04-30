package com.wposs.buc.restpapp.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VisualizarProductosActivity extends AppCompatActivity {

    ListView listView;
    ClsConexion db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_productos);

        db = new ClsConexion(this);

        final ArrayList<HashMap<String, String>> userList = db.getAllProductos();

        listView = findViewById(R.id.listView);

        final ListAdapter adapter = new SimpleAdapter(this, userList, R.layout.list_produc,
                new String[]{"titulo","descripcion","valor"}, new int[]{R.id.tvTitulo, R.id.tvDescripcion, R.id.tvValor});
        listView.setAdapter(adapter);

        int tamañao = userList.size();

        final String[] titulos = new String[tamañao];

        for(HashMap<String, String> map: userList) {
            for(Map.Entry<String, String> mapEntry: map.entrySet()) {
                String key = mapEntry.getKey();
                String value = mapEntry.getValue();
                if (key.equals("titulo")){
                    for (int i = 0; i < tamañao; i ++){
                        titulos[i] = value;
                    }
                }
            }
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String producto = titulos[position];
                Toast.makeText(VisualizarProductosActivity.this, "Data seleccionada " + producto, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
