package com.wposs.buc.restpapp.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CrearProductoPedidoActivity extends AppCompatActivity {

    ClsConexion db;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pedido_producto);

        db = new ClsConexion(this);
        final ArrayList<HashMap<String, String>> userList = db.getAllProductos();

        listView = findViewById(R.id.listView);

        final ListAdapter adapter = new SimpleAdapter(this, userList, R.layout.list_agregar_productos,
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


    }
}
