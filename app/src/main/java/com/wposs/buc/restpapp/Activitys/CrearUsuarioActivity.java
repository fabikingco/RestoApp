package com.wposs.buc.restpapp.Activitys;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.wposs.buc.restpapp.BD.Controler.ClsConexion;
import com.wposs.buc.restpapp.R;

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
                        String user = obtenerUsuario(sNombre, sApellido);
                        String name = sNombre +
                                sApellido;
                        String role = obtenerRole();
                        String status = "new";
                        Toast.makeText(this, "user: " + user + " name: " + name, Toast.LENGTH_SHORT).show();
                        //bd.crearUsuario(id, user, sDocumento, name, role, status);
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
        String apellido = sApellido.trim().replace(" ", "").toLowerCase();

        ret = primeraLetraDelNombre +
                apellido;

        return ret;
    }

    public void agregarFotoUsuario(View view) {
        Toast.makeText(this, "Aun no es posible agregar foto de usuario", Toast.LENGTH_SHORT).show();
    }
}
