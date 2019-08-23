package com.wposs.buc.restpapp.activitys;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wposs.buc.restpapp.adapters.ListProductosAgregadosAdapter;
import com.wposs.buc.restpapp.adapters.ListProductosPedidoAdapter;
import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.model.Productos;
import com.wposs.buc.restpapp.model.ProductosAgregadosPedido;
import com.wposs.buc.restpapp.model.ProductosPedidoActivo;

import java.util.ArrayList;
import java.util.Iterator;

public class CrearProductoPedidoActivity extends AppCompatActivity implements ListProductosPedidoAdapter.OnItemClickListener, ListProductosAgregadosAdapter.OnItemClickListener{

    RecyclerView recyclerView;
    BottomSheetBehavior sheetBehavior;
    LinearLayoutCompat layoutBottomSheet;
    ArrayList<Productos> productos = null;
    TextView tvNumeros , tvTotal;
    int numeros = 0;
    int total = 0;
    StringBuilder mProductosAgregados;
    ArrayList<ProductosPedidoActivo> productoAgregado;

    FirebaseFirestore firestore;
    SwipeRefreshLayout refreshLayout;
    Button btnCrearPedido;

    RecyclerView rvProductosAgregados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pedido_producto);
        refreshLayout = findViewById(R.id.refreshLayout);

        productoAgregado = new ArrayList<>();
        btnCrearPedido = findViewById(R.id.btnCrearPedido);
        btnCrearPedido.setOnClickListener(onClickCrearPedido);

        rvProductosAgregados = findViewById(R.id.rvProductosAgregados);

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
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
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

        firestore = FirebaseFirestore.getInstance();

        productos = new ArrayList<>();

        recyclerView = findViewById(R.id.rvItemsProductos);

        /*LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);*/

        GridLayoutManager glm = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(glm);

        firestore.collection("Productos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot doc : task.getResult()){
                            Productos prod = new Productos(doc.getString("id"),
                                    doc.getString("titulo"),
                                    doc.getString("valor"),
                                    doc.getString("categoria"),
                                    doc.getString("descripcion"),
                                    doc.getString("url"));
                            productos.add(prod);
                            ListProductosPedidoAdapter lpAdapter = new ListProductosPedidoAdapter(productos, getApplicationContext());
                            recyclerView.setAdapter(lpAdapter);
                            lpAdapter.setOnItemClickListener(CrearProductoPedidoActivity.this);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CrearProductoPedidoActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                Toast.makeText(CrearProductoPedidoActivity.this, "Lista actualizada", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private Button.OnClickListener onClickCrearPedido
            = new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            Toast.makeText(CrearProductoPedidoActivity.this, "BTN Crear pedido", Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void onItemClick(RecyclerView.ViewHolder item, int position, int id) {
        Productos productos = this.productos.get(position);
        mProductosAgregados = new StringBuilder();
        if(id == R.id.btnAgregar){
            Toast.makeText(this, "Agregando producto: " + productos.getNombre(), Toast.LENGTH_SHORT).show();
            int cantidad = 1;

            Iterator itr = productoAgregado.iterator();
            if (productoAgregado.size() != 0){
                while (itr.hasNext()){
                    ProductosPedidoActivo j = (ProductosPedidoActivo) itr.next();
                    if (productos.getNombre().equals(j.getName())){
                        cantidad = j.getCantidad() + 1;
                        itr.remove();
                        break;
                    }
                }
            }

            numeros ++;
            tvNumeros.setText("" + numeros);
            total += productos.getValor();
            tvTotal.setText("$" + total);
            //ProductosAgregadosPedido pedido = new ProductosAgregadosPedido(productos.getId(), productos.getNombre(), productos.getValor(), cantidad);
            int valorTotalProducto = cantidad * productos.getValor();
            ProductosPedidoActivo activo = new ProductosPedidoActivo(productos.getId(), productos.getNombre(), cantidad, productos.getValor(), valorTotalProducto, productos.getPhotoUrl());
            productoAgregado.add(activo);
            ListProductosAgregadosAdapter adapter = new ListProductosAgregadosAdapter(productoAgregado, getApplicationContext());
            rvProductosAgregados.setAdapter(adapter);
            /*rvProductosAgregados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ProductosAgregadosPedido agregado = productoAgregado.get(position);
                    Toast.makeText(CrearProductoPedidoActivity.this, "" + agregado.getName(), Toast.LENGTH_SHORT).show();
                }
            });*/
        }
    }


    @Override
    public void onItemClickAgregados(RecyclerView.ViewHolder item, int position, int id) {
        Toast.makeText(this, "Hizo Clic en el elemento", Toast.LENGTH_SHORT).show();
    }
}
