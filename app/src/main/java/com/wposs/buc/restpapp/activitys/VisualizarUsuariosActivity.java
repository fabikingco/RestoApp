package com.wposs.buc.restpapp.activitys;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.model.Usuarios;
import com.wposs.buc.restpapp.R;

public class VisualizarUsuariosActivity extends AppCompatActivity {

    ClsConexion db;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_items);

        /*db = new ClsConexion(this);

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

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                Toast.makeText(VisualizarUsuariosActivity.this, "Lista actualizada", Toast.LENGTH_SHORT).show();

            }
        });*/


    }
}
