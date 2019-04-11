package com.wposs.buc.restpapp.Activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.wposs.buc.restpapp.BD.Controler.ClsConexion;
import com.wposs.buc.restpapp.BD.Model.Usuarios;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.Tools;

public class LoginActivity extends AppCompatActivity {

    private EditText et_user, et_pw;
    private ImageView punto1, punto2, punto3, punto4, punto5, punto6;
    private String user, pass;

    public static Usuarios usuario;
    private ClsConexion bd;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
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
        bd = new ClsConexion(this);


        et_pw = findViewById(R.id.editText_Clave);

        et_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int tamanio = s.length();
                switch (tamanio) {
                    case 0:
                        et_pw.setCursorVisible(true);
                        punto1.setVisibility(View.INVISIBLE);
                        punto2.setVisibility(View.INVISIBLE);
                        punto3.setVisibility(View.INVISIBLE);
                        punto4.setVisibility(View.INVISIBLE);
                        punto5.setVisibility(View.INVISIBLE);
                        punto6.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        et_pw.setCursorVisible(false);
                        punto1.setVisibility(View.VISIBLE);
                        punto2.setVisibility(View.INVISIBLE);
                        punto3.setVisibility(View.INVISIBLE);
                        punto4.setVisibility(View.INVISIBLE);
                        punto5.setVisibility(View.INVISIBLE);
                        punto6.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        punto1.setVisibility(View.VISIBLE);
                        punto2.setVisibility(View.VISIBLE);
                        punto3.setVisibility(View.INVISIBLE);
                        punto4.setVisibility(View.INVISIBLE);
                        punto5.setVisibility(View.INVISIBLE);
                        punto6.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        punto1.setVisibility(View.VISIBLE);
                        punto2.setVisibility(View.VISIBLE);
                        punto3.setVisibility(View.VISIBLE);
                        punto4.setVisibility(View.INVISIBLE);
                        punto5.setVisibility(View.INVISIBLE);
                        punto6.setVisibility(View.INVISIBLE);
                        break;
                    case 4:
                        punto1.setVisibility(View.VISIBLE);
                        punto2.setVisibility(View.VISIBLE);
                        punto3.setVisibility(View.VISIBLE);
                        punto4.setVisibility(View.VISIBLE);
                        punto5.setVisibility(View.INVISIBLE);
                        punto6.setVisibility(View.INVISIBLE);
                        break;
                    case 5:
                        punto1.setVisibility(View.VISIBLE);
                        punto2.setVisibility(View.VISIBLE);
                        punto3.setVisibility(View.VISIBLE);
                        punto4.setVisibility(View.VISIBLE);
                        punto5.setVisibility(View.VISIBLE);
                        punto6.setVisibility(View.INVISIBLE);
                        break;
                    case 6:
                        punto1.setVisibility(View.VISIBLE);
                        punto2.setVisibility(View.VISIBLE);
                        punto3.setVisibility(View.VISIBLE);
                        punto4.setVisibility(View.VISIBLE);
                        punto5.setVisibility(View.VISIBLE);
                        punto6.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    private void findViewObjetos() {
        et_user = findViewById(R.id.editText_usuario);
        punto1 = findViewById(R.id.punto1);
        punto2 = findViewById(R.id.punto2);
        punto3 = findViewById(R.id.punto3);
        punto4 = findViewById(R.id.punto4);
        punto5 = findViewById(R.id.punto5);
        punto6 = findViewById(R.id.punto6);
    }

    public void ingresar(View view) {
        user = et_user.getText().toString();
        pass = et_pw.getText().toString();
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
            }
        }
    }
}
