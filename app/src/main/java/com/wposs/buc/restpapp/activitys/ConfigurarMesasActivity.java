package com.wposs.buc.restpapp.activitys;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wposs.buc.restpapp.Tools;
import com.wposs.buc.restpapp.adapters.ListMesasAdapter;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.model.Mesas;

import java.util.ArrayList;

public class ConfigurarMesasActivity extends AppCompatActivity implements ListMesasAdapter.OnItemClickListenerMesas{

    private SwipeRefreshLayout refreshLayout;
    private FirebaseFirestore firestore;
    private ArrayList<Mesas> mesas;
    private RecyclerView recyclerView;
    private Mesas mesaClick;

    private View parent_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_mesas);

        refreshLayout = findViewById(R.id.refreshLayout);
        parent_view = findViewById(android.R.id.content);

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
        adapter.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(RecyclerView.ViewHolder item, int position, int id) {
        mesaClick = this.mesas.get(position);
        switch (mesaClick.getStatus()){
            case "cerrada":
                showSingleChoiceDialog(1);
                break;
            case "disponible":
                showSingleChoiceDialog(0);
                break;
            case "ocupada":
                Toast.makeText(ConfigurarMesasActivity.this, "No puede modificar mesa ocupada. Finalice el pedido primero", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private String single_choice_selected;

    private static final String[] RINGTONE = new String[]{
            "Disponible", "Cerrada"
    };

    private static final String[] opciones = new String[]{
            "disponible", "cerrada"
    };

    private void showSingleChoiceDialog(int status) {
        single_choice_selected = RINGTONE[0];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar " + mesaClick.getName());
        builder.setSingleChoiceItems(RINGTONE, status, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                single_choice_selected = opciones[i];
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Snackbar.make(parent_view, "selected : " + single_choice_selected, Snackbar.LENGTH_SHORT).show();
                actualizarStatus();
            }
        });
        builder.setNegativeButton("CANCEL", null);
        builder.show();
    }

    private void actualizarStatus() {
        firestore.collection("Mesas").document(mesaClick.getId()).
                update("status", single_choice_selected).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Snackbar.make(parent_view, "Actualizacion exitosa : " + single_choice_selected, Snackbar.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ConfigurarMesasActivity.this, "Error al realizar update", Toast.LENGTH_SHORT).show();
                }
                descargarDatosMesas();
            }
        });
    }
}
