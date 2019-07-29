package com.wposs.buc.restpapp.activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.R;

import java.util.ArrayList;

public class EditarCategoriasActivity extends AppCompatActivity {

    ClsConexion bd;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_categorias);
        bd = new ClsConexion(this);
        crearBotones();
    }

    private void crearBotones() {


        ArrayList<String> categorias = bd.getAllCategorias();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categorias);

        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String categoria = adapter.getItem(position);
                Toast.makeText(EditarCategoriasActivity.this, "Data seleccionada " + categoria, Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(EditarCategoriasActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.dialog_bd_botones, null);

                builder.setView(v);
                final AlertDialog dialog = builder.show();

                TextView btnCancelar = v.findViewById(R.id.btnCancelar);
                final EditText etCategoria = v.findViewById(R.id.etCategoria);

                Button btnEliminar = v.findViewById(R.id.btnEliminar);
                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bd.deleteCategoria(categoria);
                    }
                });



                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }


}
