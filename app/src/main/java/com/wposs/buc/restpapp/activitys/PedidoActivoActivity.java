package com.wposs.buc.restpapp.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.adapters.ListProductosPedidoActivoAdapter;
import com.wposs.buc.restpapp.model.ProductosPedidoActivo;

import java.util.ArrayList;

public class PedidoActivoActivity extends AppCompatActivity {

    FirebaseFirestore mFirestore;
    RecyclerView recyclerView;
    private ArrayList<ProductosPedidoActivo> productosPedidoActivos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_activo);

        mFirestore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.rv);
        GridLayoutManager glm = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(glm);

        descargarProductosPedidoActivo();
    }

    private void descargarProductosPedidoActivo() {
        productosPedidoActivos = new ArrayList<>();
        mFirestore.collection("PedidosActivos")
                .document("6-08012020203556")
                .collection("ProductosPedidoActivo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot doc:task.getResult()){
                            ProductosPedidoActivo pedidoActivo = new ProductosPedidoActivo();
                            pedidoActivo.setId(doc.getString("id"));
                            pedidoActivo.setName(doc.getString("name"));
                            long cantid = doc.getLong("cantidad");
                            pedidoActivo.setCantidad((int) cantid);
                            long valor = doc.getLong("valorTotal");
                            pedidoActivo.setValorTotal((int) valor);
                            productosPedidoActivos.add(pedidoActivo);
                            llenarRecyclerView();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PedidoActivoActivity.this, "Error al descargar datos", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void llenarRecyclerView() {
        ListProductosPedidoActivoAdapter adapter = new ListProductosPedidoActivoAdapter(productosPedidoActivos, this);
        recyclerView.setAdapter(adapter);

    }
}
