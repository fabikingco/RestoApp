package com.wposs.buc.restpapp.Activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.wposs.buc.restpapp.Activitys.Adapters.ListUsuariosAdapter;
import com.wposs.buc.restpapp.BD.Controler.ClsConexion;
import com.wposs.buc.restpapp.BD.Model.Usuarios;
import com.wposs.buc.restpapp.R;

import java.util.ArrayList;

public class VisualizarUsuariosActivity extends AppCompatActivity {

    ClsConexion db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_items);

        db = new ClsConexion(this);

        ArrayList<Usuarios> usuarios =  db.getAllUsuarios();

        ListUsuariosAdapter adapter = new ListUsuariosAdapter(this, usuarios);

        ListView listView = findViewById(R.id.listview_flavor);
        listView.setAdapter(adapter);


    }
}
