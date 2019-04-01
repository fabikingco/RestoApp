package com.wposs.buc.restpapp.Activitys;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wposs.buc.restpapp.BD.Controler.ClsConexion;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.Tools;

import java.util.ArrayList;

public class NuevoProductoActivity extends AppCompatActivity {

    TextInputEditText etProducto, etValor, etDescripcion;
    EditText etCategoria;
    Spinner spinnerCategorias;
    String producto, categoria, descripcion;
    int valor;

    ClsConexion bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);
        bd = new ClsConexion(this);

        etProducto = findViewById(R.id.etProducto);
        etValor = findViewById(R.id.etValor);
        etDescripcion = findViewById(R.id.etDescripcion);
        spinnerCategorias = findViewById(R.id.spinnerCategorias);

        llenarSpinner();

        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoria = (String) parent.getItemAtPosition(position);

                Toast.makeText(NuevoProductoActivity.this, "Categoria seleccionada " + categoria, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void llenarSpinner() {
        ArrayList<String> categorias = new ArrayList<String>();
        try{
            categorias = bd.getAllCategorias();
        }catch (NullPointerException e){
            e.getCause();
            categorias.add("NO EXISTEN CATEGORIAS");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, categorias);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerCategorias.setAdapter(adapter);
    }

    public void nuevaCategoria(View view2) {

        AlertDialog.Builder builder = new AlertDialog.Builder(NuevoProductoActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_1_edit_text, null);

        builder.setView(v);
        final AlertDialog dialog = builder.show();

        Button btnAceptar = v.findViewById(R.id.btnAgregar);
        TextView btnCancelar = v.findViewById(R.id.btnCancelar);
        final EditText etCategoria = v.findViewById(R.id.etCategoria);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                String cat = etCategoria.getText().toString();
                ArrayList<String> categorias = bd.getAllCategorias();
                boolean existe = false;
                for(int i = 0; i < categorias.size(); i++){
                    if (cat.trim().equalsIgnoreCase(categorias.get(i)))
                        existe = true;
                }
                if (!existe) {
                    bd.crearCategoria(cat);
                }else{
                    Toast.makeText(NuevoProductoActivity.this, "Categoria ya existe", Toast.LENGTH_SHORT).show();
                }
                llenarSpinner();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void crearProducto(View view) {
        if(!categoria.equals("NO EXISTEN CATEGORIAS")) {
            producto = etProducto.getText().toString();
            valor = Integer.parseInt(etValor.getText().toString());
            descripcion = etDescripcion.getText().toString();

            if (bd.crearProducto(producto, valor, categoria, descripcion)) {
                Toast.makeText(this, "Producto creado con exito", Toast.LENGTH_SHORT).show();
                Tools.startView(this, MainActivity.class);
                finish();
            }
        } else {
            Toast.makeText(this, "Debe crear una nueva categoria", Toast.LENGTH_SHORT).show();
        }
    }
}
