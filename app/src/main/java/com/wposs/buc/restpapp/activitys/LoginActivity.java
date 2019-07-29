package com.wposs.buc.restpapp.activitys;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.bd.model.Usuarios;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.Tools;

public class LoginActivity extends AppCompatActivity {

    private EditText et_user, et_pw;
    private String user, pass;

    public static Usuarios usuario;
    private ClsConexion bd;
    private SharedPreferences sharedPreferences;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);

        bd = new ClsConexion(this);

        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        boolean loginActivo = sharedPreferences.getBoolean("loginActivo", false);
        if (loginActivo) {
            usuario = new Usuarios();
            String user = sharedPreferences.getString("user", "error");
            String name = sharedPreferences.getString("name", "error");
            String role = sharedPreferences.getString("role", "error");
            usuario.setUser(user);
            usuario.setName(name);
            usuario.setRole(role);
            Tools.startView(this, MainActivity.class);
            finish();
            return;
        }

        setContentView(R.layout.activity_login);
        findViewObjetos();
        et_pw = findViewById(R.id.editText_Clave);
    }

    private void findViewObjetos() {
        et_user = findViewById(R.id.editText_usuario);
    }

    public void ingresar(View view) {
        user = et_user.getText().toString();
        pass = et_pw.getText().toString();
        if (validateForm()) {
            usuario = bd.readUser(new Usuarios(user));
            if (usuario != null) {
                if (usuario.getUser().equals(user) && usuario.getPass().equals(pass)) {
                    // Guardar estado del login
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loginActivo", true);
                    editor.putString("user", usuario.getUser());
                    editor.putString("name", usuario.getName());
                    editor.putString("role", usuario.getRole());
                    editor.apply();

                    Tools.startView(this, MainActivity.class);
                    finish();
                } else {
                    Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Usuario no existe ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = et_user.getText().toString();
        if (TextUtils.isEmpty(email)) {
            et_user.setError("Required.");
            valid = false;
        } else {
            et_user.setError(null);
        }

        String password = et_pw.getText().toString();
        if (TextUtils.isEmpty(password)) {
            et_pw.setError("Required.");
            valid = false;
        } else {
            et_pw.setError(null);
        }

        return valid;
    }

}
