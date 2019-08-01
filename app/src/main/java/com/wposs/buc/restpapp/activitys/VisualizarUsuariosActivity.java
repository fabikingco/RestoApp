package com.wposs.buc.restpapp.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.wposs.buc.restpapp.adapters.ListUsuariosAdapter;
import com.wposs.buc.restpapp.model.Usuarios;
import com.wposs.buc.restpapp.R;

import java.util.ArrayList;

public class VisualizarUsuariosActivity extends AppCompatActivity {


    SwipeRefreshLayout refreshLayout;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_items);

        mFirestore = FirebaseFirestore.getInstance();

        final ArrayList<Usuarios> usuarios = new ArrayList<>();

        mFirestore.collection("usuarios")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Log.d("Obtener usuarios", document.getId() + " => " + document.getData());
                                usuarios.add(new Usuarios(document.getString("user"),
                                        document.getString("pass"),
                                        document.getString("name"),
                                        document.getString("role"),
                                        document.getString("status")));

                            }
                            cargarLista(usuarios);
                        }
                    }
                });




    }

    private void cargarLista(final ArrayList<Usuarios> usuarios) {
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

        refreshLayout = findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                Toast.makeText(VisualizarUsuariosActivity.this, "Lista actualizada", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
