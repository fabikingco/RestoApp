package com.wposs.buc.restpapp.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.wposs.buc.restpapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText et_user, et_pw;
    ImageView punto1, punto2, punto3, punto4, punto5, punto6;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_login);
        findViewObjetos();



        et_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int tamanio=s.length();
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
        et_pw = findViewById(R.id.editText_Clave);
        punto1 = findViewById(R.id.punto1);
        punto2 = findViewById(R.id.punto2);
        punto3 = findViewById(R.id.punto3);
        punto4 = findViewById(R.id.punto4);
        punto5 = findViewById(R.id.punto5);
        punto6 = findViewById(R.id.punto6);
        btnLogin = findViewById(R.id.btLogin);
    }
}
