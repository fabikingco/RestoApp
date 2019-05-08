package com.wposs.buc.restpapp.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.wposs.buc.restpapp.adapters.ListProductosPedidoAdapter;
import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.bd.model.Productos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CrearProductoPedidoActivity extends AppCompatActivity {

    ClsConexion db;
    RecyclerView recyclerView;
    int numeros = 0;
    TextView tvNumeros, tvTotal;
    int total;

    public CrearProductoPedidoActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pedido_producto);



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

    public void sumarAgregarProducto(ArrayList<Productos> productos, int posision) {
        /*Productos productos1 = productos.get(posision);
        numeros ++;
        tvNumeros = findViewById(R.id.tvNumero);
        tvTotal = findViewById(R.id.tvTotal);

        tvNumeros.setText(String.valueOf(numeros));
        total += productos1.getValor();

        tvTotal.setText("$" + total);*/
    }
}
