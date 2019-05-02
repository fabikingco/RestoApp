package com.wposs.buc.restpapp.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wposs.buc.restpapp.adapters.ListUsuariosAdapter;
import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.bd.model.Usuarios;
import com.wposs.buc.restpapp.R;

import java.util.ArrayList;

public class VisualizarUsuariosActivity extends AppCompatActivity {

    ClsConexion db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_items);

        db = new ClsConexion(this);

        final ArrayList<Usuarios> usuarios =  db.getAllUsuarios();

        ListUsuariosAdapter adapter = new ListUsuariosAdapter(this, usuarios);

        ListView listView = findViewById(R.id.listview_flavor);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Usuarios mUsuarios = usuarios.get(position);
                Toast.makeText(VisualizarUsuariosActivity.this, "" + mUsuarios.getName() + " " + mUsuarios.getPass(), Toast.LENGTH_SHORT).show();


            }
        });


    }
}
