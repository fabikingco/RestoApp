package com.wposs.buc.restpapp.activitys;

import android.app.AlertDialog;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.Tools;
import com.wposs.buc.restpapp.firebase.Collections;
import com.wposs.buc.restpapp.model.Productos;
import com.wposs.buc.restpapp.model.Usuarios;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    private EditText et_user, et_pw;
    private String user, pass;

    public static Usuarios usuario;
    private ClsConexion bd;
    private SharedPreferences sharedPreferences;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        bd = new ClsConexion(this);
        setContentView(R.layout.activity_login);
        dialog = new SpotsDialog.Builder().setContext(this).build();
        findViewObjetos();

    }

    private void findViewObjetos() {
        et_user = findViewById(R.id.et_user);
        et_pw = findViewById(R.id.et_pw);
    }

    public void ingresar(View view) {
        dialog.setMessage("Ingresando...");
        dialog.show();
        user = et_user.getText().toString();
        pass = et_pw.getText().toString();

        if (validateForm(user, pass)) {
            mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Inicio de sesion exitoso", Toast.LENGTH_SHORT).show();
                        mFirestore.collection(Collections.usuarios)
                                .document(user)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot doc = task.getResult();
                                        Usuarios usuarioLogin = new Usuarios(doc.getString(Collections.Usuarios.id),
                                                doc.getString(Collections.Usuarios.user),
                                                doc.getString(Collections.Usuarios.pass),
                                                doc.getString(Collections.Usuarios.name),
                                                doc.getString(Collections.Usuarios.role),
                                                doc.getString(Collections.Usuarios.status),
                                                doc.getString(Collections.Usuarios.photoUrl),
                                                doc.getString(Collections.Usuarios.restaurante));
                                        boolean actualizarUsuario = bd.guardarUsuarioActual(usuarioLogin);
                                        if (actualizarUsuario){
                                            dialog.dismiss();
                                            Tools.startView(LoginActivity.this, MainActivity2.class, true);
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Error al guardar login en BD", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        dialog.dismiss();
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
        if (currentUser != null) {
            Log.d("Login Status", "Logueado, " + currentUser.getEmail());
            Intent intent = new Intent();
            intent.putExtra("currentUser", currentUser.getUid());
            intent.setClass(this, MainActivity2.class);
            startActivity(intent);
            this.finish();
        } else {
            Log.d("Login Stauts", "No es logueado");
        }
    }
}
