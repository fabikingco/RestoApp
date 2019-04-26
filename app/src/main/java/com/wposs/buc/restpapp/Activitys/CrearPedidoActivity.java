package com.wposs.buc.restpapp.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.wposs.buc.restpapp.Activitys.Adapters.ListMesasAdapter;
import com.wposs.buc.restpapp.Activitys.Adapters.ListUsuariosAdapter;
import com.wposs.buc.restpapp.BD.Controler.ClsConexion;
import com.wposs.buc.restpapp.BD.Model.Mesas;
import com.wposs.buc.restpapp.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_items);
        bd = new ClsConexion(this);

        ArrayList<Mesas> mesas = bd.getAllMesas();

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
                Toast.makeText(CrearPedidoActivity.this, "" + mMesas.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
