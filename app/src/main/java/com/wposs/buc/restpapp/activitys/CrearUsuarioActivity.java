package com.wposs.buc.restpapp.activitys;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.R;

import java.util.ArrayList;

public class CrearUsuarioActivity extends AppCompatActivity {

    EditText etDocumento, etNombre, etApellido;
    RadioButton rbAdmin, rbCaja, rbMesero;
    ClsConexion bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);
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
                                if (bd.crearUsuario(id, user, pass, name, role, status)) {
                                    Toast.makeText(this, "Usuario " + user + " creado con exito", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
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
        try {
            ArrayList<String> usuarios = bd.getAllUsuariosUser();
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).equals(user)) {
                    ret = false;
                    Toast.makeText(this, "Usuario ya existe", Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
            Log.e("Error ", ""+e);
        }
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
                apellido;

        return ret;
    }

    public void agregarFotoUsuario(View view) {
        Toast.makeText(this, "Aun no es posible agregar foto de usuario", Toast.LENGTH_SHORT).show();
    }
}
