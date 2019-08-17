package com.wposs.buc.restpapp.activitys;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wposs.buc.restpapp.adapters.ListMesasAdapter;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.model.Mesas;

import java.util.ArrayList;

public class ConfigurarMesasActivity extends AppCompatActivity {

    private SwipeRefreshLayout refreshLayout;
    private FirebaseFirestore firestore;
    private ArrayList<Mesas> mesas;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_mesas);

        refreshLayout = findViewById(R.id.refreshLayout);

        recyclerView = findViewById(R.id.rvMesas);
        GridLayoutManager glm = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(glm);
        firestore = FirebaseFirestore.getInstance();

        descargarDatosMesas();
    }

    private void descargarDatosMesas(){
        mesas = new ArrayList<>();

        firestore.collection("Mesas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot doc:task.getResult()){
                            Mesas prod = new Mesas(doc.getString("id"),
                                    doc.getString("name"),
                                    doc.getString("status"));
                            mesas.add(prod);
                            crearBotonesDeMesas(mesas);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ConfigurarMesasActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void crearBotonesDeMesas(final ArrayList<Mesas> mesas) {

        if (mesas.size() == 0){
            Toast.makeText(this, "No existen mesas para finalizar", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
            return;
        }

        ListMesasAdapter adapter = new ListMesasAdapter(mesas, this);
        recyclerView.setAdapter(adapter);

        //adapter.setOnItemClickListener(this);

    }

    public void agregarMesa(View view) {
        int intNewMesa = mesas.size() + 1;
        final String idNewMesa = String.valueOf(intNewMesa);
        final String nameNewMesa = "Mesa " + idNewMesa;
        final String statusNewMesa = "disponible";
        Mesas mesaNueva = new Mesas(idNewMesa, nameNewMesa, statusNewMesa);
        firestore.collection("Mesas").document(idNewMesa)
                .set(mesaNueva).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    descargarDatosMesas();
                    Toast.makeText(ConfigurarMesasActivity.this, nameNewMesa + "Agregada con exito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ConfigurarMesasActivity.this, "Error al crear nueva mesa", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void eliminarMesas(View view) {
        int intNewMesa = mesas.size();
        final String idDeleteMesa = String.valueOf(intNewMesa);

        firestore.collection("Mesas").document(idDeleteMesa)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        String status;
                        if (documentSnapshot != null) {
                            status = documentSnapshot.getString("status");
                            switch (status){
                                case "disponible":
                                    eliminarMesa(idDeleteMesa);
                                    break;
                                case "cerrada":
                                    eliminarMesa(idDeleteMesa);
                                    break;
                                case "ocupada":
                                    Toast.makeText(ConfigurarMesasActivity.this, "No se puede eliminar una mesa ocupada", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }
                });
    }

    private void eliminarMesa(String idDeleteMesa) {
        firestore.collection("Mesas").document(idDeleteMesa)
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ConfigurarMesasActivity.this, "Mesa eliminada con exito", Toast.LENGTH_SHORT).show();
                    descargarDatosMesas();
                }
            }
        });
    }

    public void otrasOpciones(View view) {
        Toast.makeText(this, "btnOtrasOpciones", Toast.LENGTH_SHORT).show();
    }
}
