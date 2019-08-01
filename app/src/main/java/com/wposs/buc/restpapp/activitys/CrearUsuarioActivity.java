package com.wposs.buc.restpapp.activitys;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.model.Usuarios;

import java.util.ArrayList;

public class CrearUsuarioActivity extends AppCompatActivity {

    EditText etDocumento, etNombre, etApellido;
    RadioButton rbAdmin, rbCaja, rbMesero;
    ClsConexion bd;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("Crear usuarios");
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        bd = new ClsConexion(this);

        etDocumento = findViewById(R.id.etDocumento);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        rbAdmin = findViewById(R.id.rbAdmin);
        rbCaja = findViewById(R.id.rbCaja);
        rbMesero = findViewById(R.id.rbMesero);
    }

    public void cancelarCrearUsuario(View view) {
    }

    public void aceptarCrearUsuario(View view) {
        String sNombre = etNombre.getText().toString().trim();
        String sApellido = etApellido.getText().toString().trim();
        String sDocumento = etDocumento.getText().toString().trim();

        if (!sNombre.isEmpty()) {
            if (!sApellido.isEmpty()) {
                if (!sDocumento.isEmpty()) {
                    if (verificarRadio()) {
                        int id = Integer.parseInt(sDocumento);
                        if (verificarIdUser(id)) {
                            String user = obtenerUsuario(sNombre, sApellido);
                            if (verificarUsuario(user)) {
                                String pass = crearPassword(sDocumento);
                                String name = sNombre +
                                        " " +
                                        sApellido;
                                String role = obtenerRole();
                                String status = "new";
//                                if (bd.crearUsuario(id, user, pass, name, role, status)) {
//                                    Toast.makeText(this, "Usuario " + user + " creado con exito", Toast.LENGTH_SHORT).show();
//                                    finish();
//                                }
                                createUser(user, pass, name, role, status);
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "Campo de documento vacio", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Campo de apellido vacio", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Campo de nombre vacio", Toast.LENGTH_SHORT).show();
        }

    }

    private String crearPassword(String sDocumento) {
        int len = sDocumento.length();
        return sDocumento.substring(len-6, len);
    }

    private boolean verificarUsuario(String user) {
        boolean ret = true;
        /**
         * Aqui agregar verificacion de usuarios para no crear usuarios repetidos.
         */
//        try {
//            ArrayList<String> usuarios = bd.getAllUsuariosUser();
//            for (int i = 0; i < usuarios.size(); i++) {
//                if (usuarios.get(i).equals(user)) {
//                    ret = false;
//                    Toast.makeText(this, "Usuario ya existe", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }catch (Exception e){
//            Log.e("Error ", ""+e);
//        }
        return ret;
    }

    private boolean verificarIdUser(int id) {
        boolean ret = true;
        try {
            ArrayList<Integer> usuarios = bd.getAllUsuariosId();
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i) == id ){
                    ret = false;
                    Toast.makeText(this, "Este documento ya tiene usuario registrado", Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
            Log.e("Error ", ""+e);
        }
        return ret;
    }

    private boolean verificarRadio() {
        boolean ret = true;
        if (!rbAdmin.isChecked()) {
            if (!rbCaja.isChecked()) {
                if (!rbMesero.isChecked()) {
                    ret = false;
                    Toast.makeText(this, "No ha seleccionado ninguna opcion de rol", Toast.LENGTH_SHORT).show();
                }
            }
        }

        return ret;
    }

    private String obtenerRole() {
        String ret = null;
        if (rbAdmin.isChecked())
            ret = rbAdmin.getText().toString();
        if (rbCaja.isChecked())
            ret = rbCaja.getText().toString();
        if (rbMesero.isChecked())
            ret = rbMesero.getText().toString();
        return ret;
    }

    private String obtenerUsuario(String sNombre, String sApellido) {
        String ret;
        String primeraLetraDelNombre = sNombre.substring(0,1).toLowerCase();
        String apellido;
        if (!sApellido.contains(" ")){
            apellido = sApellido.trim().toLowerCase();
        }else {
            apellido = sApellido.trim().substring(0,sApellido.indexOf(" ")).toLowerCase();
        }

        ret = primeraLetraDelNombre +
                apellido + "@restoapp.com.co";

        return ret;
    }

    public void agregarFotoUsuario(View view) {
        Toast.makeText(this, "Aun no es posible agregar foto de usuario", Toast.LENGTH_SHORT).show();
    }

    private void createUser(final String email, final String password, final String name, final String role, final String status){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Usuarios usuarios = new Usuarios();
                    usuarios.setUser(email);
                    usuarios.setPass(password);
                    usuarios.setName(name);
                    usuarios.setRole(role);
                    usuarios.setStatus(status);
                    mFirestore.collection("usuarios").document(email)
                            .set(usuarios).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Log.d("Creacion de Cuenta", "Documento del usuario creado correctamente");
                            } else {
                                Log.e("Creacion de Cuenta", "Fallo creacion del documento del usuario ");
                            }
                        }
                    });
                    Log.d("Creacion de Cuenta", "Creada exitosamente con email " + email);
                    Toast.makeText(CrearUsuarioActivity.this, "Nuevo usuario creado con exito. " + email + " y contraseña" + password, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.d("Creacion de Cuenta", "No fue posible crear la cuenta " + task.getException().toString());
                }
            }
        });
    }
}
