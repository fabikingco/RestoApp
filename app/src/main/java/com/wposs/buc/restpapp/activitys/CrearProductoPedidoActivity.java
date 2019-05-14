package com.wposs.buc.restpapp.activitys;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.wposs.buc.restpapp.adapters.ListProductosPedidoAdapter;
import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.bd.model.Productos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CrearProductoPedidoActivity extends AppCompatActivity implements ListProductosPedidoAdapter.OnItemClickListener{

    ClsConexion db;
    RecyclerView recyclerView;
    BottomSheetBehavior sheetBehavior;
    LinearLayout layoutBottomSheet;
    ArrayList<Productos> productos = null;
    TextView tvNumeros , tvTotal;
    int numeros = 0;
    int total = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pedido_producto);

        layoutBottomSheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        tvNumeros = findViewById(R.id.tvNumeros);
        tvNumeros.setText("" + numeros);
        tvTotal = findViewById(R.id.tvTotal);
        tvTotal.setText("$" + total);
        layoutBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        Toast.makeText(CrearProductoPedidoActivity.this, "Abierta", Toast.LENGTH_SHORT).show();
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        Toast.makeText(CrearProductoPedidoActivity.this, "Cerrada", Toast.LENGTH_SHORT).show();
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }
            @Override
            public void onSlide(@NonNull View view, float v) { }
        });


        db = new ClsConexion(this);
        productos = db.getAllProductosP();

        recyclerView = findViewById(R.id.rvItemsProductos);

        /*LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);*/

        GridLayoutManager glm = new GridLayoutManager(this, 2);


        recyclerView.setLayoutManager(glm);

        ListProductosPedidoAdapter lpAdapter = new ListProductosPedidoAdapter(productos);
        recyclerView.setAdapter(lpAdapter);
        lpAdapter.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(RecyclerView.ViewHolder item, int position, int id) {
        Productos productos = this.productos.get(position);
        if(id == R.id.btnAgregar){
            Toast.makeText(this, "Agregando producto: " + productos.getNombre(), Toast.LENGTH_SHORT).show();
            numeros ++;
            tvNumeros.setText("" + numeros);
            total += productos.getValor();
            tvTotal.setText("$" + total);
        }

    }
}
