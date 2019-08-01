package com.wposs.buc.restpapp.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.Tools;
import com.wposs.buc.restpapp.model.Categorias;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NuevoProductoActivity extends AppCompatActivity {

    EditText etProducto, etValor, etDescripcion;
    EditText etCategoria;
    Spinner spinnerCategorias;
    String producto, categoria, descripcion;
    String valor;
    ArrayList<String> cateList;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();
        cateList = llenarSpinner();
    }

    private ArrayList<String> llenarSpinner() {

        final ArrayList<String> cateList = new ArrayList<String>();

        firestore.collection("Categorias")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                cateList.add(document.getString("name"));
                                Log.d("Obtener Categorias", document.getId() + " => " + document.getData());
                            }
                            cargarWidgets();
                        } else {
                            Log.d("Obtener Categorias", "Error getting documents: ", task.getException());
                        }
                    }
                });

        return cateList;
    }

    private void cargarWidgets() {
        setContentView(R.layout.activity_nuevo_producto);

        etProducto = findViewById(R.id.etProducto);
        etValor = findViewById(R.id.etValor);
        etDescripcion = findViewById(R.id.etDescripcion);
        spinnerCategorias = findViewById(R.id.spinnerCategorias);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, cateList);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerCategorias.setAdapter(adapter);

        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoria = (String) parent.getItemAtPosition(position);
                Toast.makeText(NuevoProductoActivity.this, "Selecciono -> " + categoria, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void nuevaCategoria(View view2) {

        /*AlertDialog.Builder builder = new AlertDialog.Builder(NuevoProductoActivity.this);
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
        });*/
    }

    public void crearProducto(View view) {
        if(!categoria.equals("NO EXISTEN CATEGORIAS")) {

            producto = etProducto.getText().toString();
            if (producto.isEmpty()) {
                Toast.makeText(this, "Nombre del pruducto esta vacio", Toast.LENGTH_SHORT).show();
                etProducto.setBackgroundResource(R.drawable.edittext_pichi_error);
                return;
            }

            if (etValor.getText().toString().isEmpty()) {
                Toast.makeText(this, "Valor del producto esta vacio", Toast.LENGTH_SHORT).show();
                etValor.setBackgroundResource(R.drawable.edittext_pichi_error);
                return;
            }
            valor = etValor.getText().toString();

            descripcion = etDescripcion.getText().toString();
            if (descripcion.isEmpty()) {
                Toast.makeText(this, "Descripcion del pruducto esta vacio", Toast.LENGTH_SHORT).show();
                etDescripcion.setBackgroundResource(R.drawable.edittext_pichi_error);
                return;
            }

            String id = UUID.randomUUID().toString();
            Map<String,Object> todo = new HashMap<>();
            todo.put("id", id);
            todo.put("categoria", categoria);
            todo.put("descripcion", descripcion);
            todo.put("titulo", producto);
            todo.put("valor", valor);
            firestore.collection("Productos").document(id)
                    .set(todo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    //Refresh data
                    Toast.makeText(NuevoProductoActivity.this, "Producto creado con exito", Toast.LENGTH_SHORT).show();
                    Tools.startView(NuevoProductoActivity.this, MainActivity.class);
                    finish();
                }
            });

        } else {
            Toast.makeText(this, "Debe crear una nueva categoria", Toast.LENGTH_SHORT).show();
        }
    }

    public void agregarFotoProducto(View view) {
        Toast.makeText(this, "Aun no es posible agregar foto de producto", Toast.LENGTH_SHORT).show();
    }
}
