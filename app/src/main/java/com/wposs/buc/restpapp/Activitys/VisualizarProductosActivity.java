package com.wposs.buc.restpapp.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.wposs.buc.restpapp.BD.Controler.ClsConexion;
import com.wposs.buc.restpapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class VisualizarProductosActivity extends AppCompatActivity {

    ListView listView;
    ClsConexion db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_productos);

        db = new ClsConexion(this);

        ArrayList<HashMap<String, String>> userList = db.GetProdcutos();

        listView = findViewById(R.id.listView);

        ListAdapter adapter = new SimpleAdapter(this, userList, R.layout.list_produc,
                new String[]{"titulo","descripcion","valor"}, new int[]{R.id.tvTitulo, R.id.tvDescripcion, R.id.tvValor});
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
}
