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
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wposs.buc.restpapp.adapters.ListProductosPedidoAdapter;
import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.model.Productos;

import java.util.ArrayList;

public class CrearProductoPedidoActivity extends AppCompatActivity implements ListProductosPedidoAdapter.OnItemClickListener{

    ClsConexion db;
    RecyclerView recyclerView;
    BottomSheetBehavior sheetBehavior;
    LinearLayoutCompat layoutBottomSheet;
    ArrayList<Productos> productos = null;
    TextView tvNumeros , tvTotal;
    int numeros = 0;
    int total = 0;

    FirebaseFirestore firestore;
    SwipeRefreshLayout refreshLayout;

    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pedido_producto);

        refreshLayout = findViewById(R.id.refreshLayout);

        //dialog = new AlertDialog(this);

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

        firestore = FirebaseFirestore.getInstance();

        db = new ClsConexion(this);
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
                                    doc.getString("descripcion"));
                            productos.add(prod);
                            ListProductosPedidoAdapter lpAdapter = new ListProductosPedidoAdapter(productos);
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



        /*ListProductosPedidoAdapter lpAdapter = new ListProductosPedidoAdapter(productos);
        recyclerView.setAdapter(lpAdapter);
        lpAdapter.setOnItemClickListener(this);*/

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
