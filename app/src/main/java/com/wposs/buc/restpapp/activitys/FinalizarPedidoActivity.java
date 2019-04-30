package com.wposs.buc.restpapp.Activitys;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wposs.buc.restpapp.Activitys.Adapters.ListMesasAdapter;
import com.wposs.buc.restpapp.BD.Controler.ClsConexion;
import com.wposs.buc.restpapp.BD.Model.Mesas;
import com.wposs.buc.restpapp.R;

import java.util.ArrayList;

public class FinalizarPedidoActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    ClsConexion bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_items);
        bd = new ClsConexion(this);

        ArrayList<Mesas> mesas = bd.getAllMesasWhereOcupadas();

        crearBotonesDeMesas(mesas);
    }

    private void crearBotonesDeMesas(final ArrayList<Mesas> mesas) {

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
