package com.wposs.buc.restpapp.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

        mAuth = FirebaseAuth.getInstance();
        /*bd = new ClsConexion(this);

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
*/
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

        if (validateForm(user, pass)) {
            mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Inicio de sesion exitoso", Toast.LENGTH_SHORT).show();
                        Tools.startView(LoginActivity.this, MainActivity.class);
                    } else {
                        Toast.makeText(LoginActivity.this, "Inicio de sesion fallido", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            /*usuario = bd.readUser(new Usuarios(user));
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
            }*/
        }
    }

    private boolean validateForm(String email, String password) {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            et_user.setError("Required.");
            valid = false;
        } else {
            et_user.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            et_pw.setError("Required.");
            valid = false;
        } else {
            et_pw.setError(null);
        }

        return valid;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateStateUser(currentUser);
    }

    private void updateStateUser(FirebaseUser currentUser) {
        if (currentUser != null){
            Log.d("Login Status", "Logueado, " + currentUser.getDisplayName());
            Intent intent = new Intent();
            intent.putExtra("currentUser", currentUser.getUid());
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
        } else {
            Log.d("Login Stauts", "No es logueado");
        }
    }

    private void createUser( final String email, final String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d("Creacion de Cuenta", "Creada exitosamente con email " + email);
                } else {
                    Log.d("Creacion de Cuenta", "No fue posible crear la cuenta " + task.getException().toString());
                }
            }
        });
    }

    private void cerrarSesion(){
        mAuth.signOut();
    }
}
