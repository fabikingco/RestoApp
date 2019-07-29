package com.wposs.buc.restpapp.activitys;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wposs.buc.restpapp.Tools;
import com.wposs.buc.restpapp.adapters.ListMesasAdapter;
import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.bd.model.Mesas;
import com.wposs.buc.restpapp.R;

import java.util.ArrayList;

public class FinalizarPedidoActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ClsConexion bd;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_items);
        bd = new ClsConexion(this);

        ArrayList<Mesas> mesas = bd.getAllMesasWhereOcupadas();

        crearBotonesDeMesas(mesas);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                Toast.makeText(FinalizarPedidoActivity.this, "Lista actualizada", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void crearBotonesDeMesas(final ArrayList<Mesas> mesas) {

        if (mesas.size() == 0){
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
                Toast.makeText(FinalizarPedidoActivity.this, "" + mMesas.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
