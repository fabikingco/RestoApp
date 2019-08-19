package com.wposs.buc.restpapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.Tools;
import com.wposs.buc.restpapp.activitys.CrearProductoPedidoActivity;
import com.wposs.buc.restpapp.activitys.MainActivity;
import com.wposs.buc.restpapp.adapters.ListMesasAdapter;
import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.model.Mesas;

import java.util.ArrayList;


public class MesasFragment extends Fragment  implements ListMesasAdapter.OnItemClickListenerMesas{

    private SwipeRefreshLayout refreshLayout;
    private FirebaseFirestore firestore;
    private ArrayList<Mesas> mesas;
    private RecyclerView recyclerView;

    public MesasFragment() {
        // Required empty public constructor
    }

    public static MesasFragment newInstance(String param1, String param2) {
        MesasFragment fragment = new MesasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mesas, container, false);
        refreshLayout = view.findViewById(R.id.refreshLayout);

        recyclerView = view.findViewById(R.id.rvMesas);
        GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(glm);


        firestore = FirebaseFirestore.getInstance();

        descargarDatosMesas(view);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                descargarDatosMesas(view);
                //crearBotonesDeMesas(mesas);
                Toast.makeText(getActivity(), "Lista actualizada", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }


    private void descargarDatosMesas(View view){
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
                            crearBotonesDeMesas(mesas, view);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void crearBotonesDeMesas(final ArrayList<Mesas> mesas, View view) {

        if (mesas.size() == 0){
            Toast.makeText(getActivity(), "No existen mesas para finalizar", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            return;
        }

        ListMesasAdapter adapter = new ListMesasAdapter(mesas, getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        /*lpAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Mesas mMesas = mesas.get(position);
                //Toast.makeText(CrearPedidoActivity.this, "" + mMesas.getName(), Toast.LENGTH_SHORT).show();
                if (mMesas.getStatus().equals("cerrada")){
                    Snackbar.make(view, "Mesa no disponible", Snackbar.LENGTH_LONG).show();
                } else {
                    Tools.startView(getActivity(), CrearProductoPedidoActivity.class);
                }
            }
        });*/
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder item, int position, int id) {
        Mesas mMesas = mesas.get(position);
        //Toast.makeText(CrearPedidoActivity.this, "" + mMesas.getName(), Toast.LENGTH_SHORT).show();
        switch (mMesas.getStatus()){
            case "cerrada":
                Toast.makeText(getActivity(), "La mesa esta cerrada", Toast.LENGTH_SHORT).show();
                break;
            case "disponible":
                Toast.makeText(getActivity(), "La mesa esta disponible ", Toast.LENGTH_SHORT).show();
                Tools.startView(getActivity(), CrearProductoPedidoActivity.class, false);
                break;
            case "ocupada":
                Toast.makeText(getActivity(), "La mesa esta ocupada ", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
