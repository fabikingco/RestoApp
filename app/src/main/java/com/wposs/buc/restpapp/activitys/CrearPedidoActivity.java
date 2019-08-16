package com.wposs.buc.restpapp.activitys;

import android.content.SharedPreferences;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wposs.buc.restpapp.adapters.ListMesasAdapter;
import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.model.Mesas;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.Tools;

import java.util.ArrayList;


/**
 * 1. Consultar numero de mesas y domicilios activos y seleccionar mesa o domicilio.
 * 2. Visualizar categorias
 * 3. Visualizar productos de la categoria agregada
 * 4. Al seleccionar productos poder agregar cantidad de productos o algun comentario.
 * 5. Generar un boton o un baner inferior con la cantidad de productos y el monto hasta el momento.
 *      Al hacer click se despliega una vista. Esa vista mostrara los productos agregados y el monto total.
 *      Tendra un boton para enviar pedido o para cancelarlo.  *
 */
public class CrearPedidoActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ClsConexion bd;
    SwipeRefreshLayout refreshLayout;

    FirebaseFirestore firestore;
    ArrayList<Mesas> mesas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_items);
        bd = new ClsConexion(this);

        firestore = FirebaseFirestore.getInstance();

        refreshLayout = findViewById(R.id.refreshLayout);

        descargarDatosMesas();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                descargarDatosMesas();
                //crearBotonesDeMesas(mesas);
                Toast.makeText(CrearPedidoActivity.this, "Lista actualizada", Toast.LENGTH_SHORT).show();

            }
        });
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
                        Toast.makeText(CrearPedidoActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void crearBotonesDeMesas(final ArrayList<Mesas> mesas) {

        /*if (mesas.size() == 0){
            Toast.makeText(this, "No existen mesas para finalizar", Toast.LENGTH_SHORT).show();
            Tools.startView(this, MainActivity.class);
            return;
        }

        ListMesasAdapter adapter = new ListMesasAdapter(this, mesas);

        ListView listView = findViewById(R.id.listview_flavor);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Mesas mMesas = mesas.get(position);
                //Toast.makeText(CrearPedidoActivity.this, "" + mMesas.getName(), Toast.LENGTH_SHORT).show();
                if (mMesas.getStatus().equals("cerrada")){
                    Snackbar.make(view, "Mesa no disponible", Snackbar.LENGTH_LONG).show();
                } else {
                    Tools.startView(CrearPedidoActivity.this, CrearProductoPedidoActivity.class);
                }
            }
        });*/
    }
}
