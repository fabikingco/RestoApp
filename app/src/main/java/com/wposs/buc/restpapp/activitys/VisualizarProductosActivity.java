package com.wposs.buc.restpapp.activitys;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wposs.buc.restpapp.adapters.ListProductosAdapter;
import com.wposs.buc.restpapp.adapters.ListProductosPedidoAdapter;
import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.bd.model.Productos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VisualizarProductosActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    SwipeRefreshLayout refreshLayout;
    ArrayList<Productos> productos = null;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_items);

        firestore = FirebaseFirestore.getInstance();
        refreshLayout = findViewById(R.id.refreshLayout);
        listView = findViewById(R.id.listview_flavor);

        productos = new ArrayList<>();

        firestore.collection("Productos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot doc:task.getResult()){
                            Productos prod = new Productos(doc.getString("id"),
                                    doc.getString("titulo"),
                                    doc.getString("valor"),
                                    doc.getString("categoria"),
                                    doc.getString("descripcion"));
                            productos.add(prod);

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(VisualizarProductosActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                Toast.makeText(VisualizarProductosActivity.this, "Lista actualizada", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void cargarCapos(){
        ListProductosAdapter adapter = new ListProductosAdapter(VisualizarProductosActivity.this, productos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Productos mProductos = productos.get(position);
                Toast.makeText(VisualizarProductosActivity.this, "" + mProductos.getNombre(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
