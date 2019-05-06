package com.wposs.buc.restpapp.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.wposs.buc.restpapp.adapters.ListProductosPedidoAdapter;
import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.bd.model.Productos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CrearProductoPedidoActivity extends AppCompatActivity {

    ClsConexion db;
    ListView listView;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_items_rv);

        db = new ClsConexion(this);
        final ArrayList<Productos> productos = db.getAllProductosP();

        recyclerView = findViewById(R.id.rvItemsProductos);

        /*LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);*/

        GridLayoutManager glm = new GridLayoutManager(this, 2);


        recyclerView.setLayoutManager(glm);

        ListProductosPedidoAdapter lpAdapter = new ListProductosPedidoAdapter(productos);
        recyclerView.setAdapter(lpAdapter);




    }
}
