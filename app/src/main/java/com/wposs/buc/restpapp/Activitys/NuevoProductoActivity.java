package com.wposs.buc.restpapp.Activitys;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wposs.buc.restpapp.BD.Controler.ClsConexion;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.Tools;

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

        etProducto = findViewById(R.id.etProducto);
        etValor = findViewById(R.id.etValor);
        etDescripcion = findViewById(R.id.etDescripcion);

        bd = new ClsConexion(this);
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
                categoria = etCategoria.getText().toString();
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

        producto = etProducto.getText().toString();
        valor = Integer.parseInt(etValor.getText().toString());
        descripcion = etDescripcion.getText().toString();

        if (bd.crearProducto(producto, valor, categoria, descripcion)){
            Toast.makeText(this, "Producto creado con exito", Toast.LENGTH_SHORT).show();
            Tools.startView(this, MainActivity.class);
            finish();
        }


    }
}
