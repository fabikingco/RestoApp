package com.wposs.buc.restpapp.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wposs.buc.restpapp.BD.Controler.ClsConexion;
import com.wposs.buc.restpapp.R;

import java.util.ArrayList;


/**
 * 1. Consultar numero de mesas y domicilios activos y seleccionar mesa o domicilio.
 * 2. Visualizar categorias
 * 3. Visualizar productos de la categoria agregada
 * 4. Al seleccionar productos poder agregar cantidad de productos o algun comentario.
 * 5. Generar un boton o un baner inferior con la cantidad de productos y el monto hasta el momento.
 *      Al hacer click se despliega una vista. Esa vista mostrara los productos agregados y el monto total.
 *      Tendra un boton para enviar pedido o para cancelarlo.  *
 */
public class CrearPedidoActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ClsConexion bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pedido);
        bd = new ClsConexion(this);

        ArrayList<String> mesas = bd.getAllMesas();

        sharedPreferences = getSharedPreferences("configuracionRestaurante", Context.MODE_PRIVATE);
        boolean infoDomicilios = sharedPreferences.getBoolean("domicilios", false);

        crearBotonesDeMesas(mesas, infoDomicilios);
    }

    private void crearBotonesDeMesas(ArrayList<String> mesas, boolean infoDomicilios) {

        LinearLayout linearLayout = findViewById(R.id.linearBotones);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 120);
        lp.setMargins(0, 10, 0, 0);

        int numMesas = mesas.size();

        Button[] buttons = new Button[numMesas];

        for (int i = 0; i < numMesas; i++){
            buttons[i] = new Button(this);
            buttons[i].setBackgroundResource(R.drawable.button_color);
            buttons[i].setLayoutParams(lp);
            buttons[i].setText(mesas.get(i));
            buttons[i].setTextColor(Color.parseColor("#0F265C"));
            buttons[i].setTextSize(20);
            buttons[i].setAllCaps(false);
            linearLayout.addView(buttons[i]);
        }

        if(infoDomicilios){
            Button domicilios = new Button(this);
            domicilios.setBackgroundResource(R.drawable.button_color);
            domicilios.setLayoutParams(lp);
            domicilios.setText("Domicilios");
            domicilios.setTextColor(Color.parseColor("#0F265C"));
            domicilios.setTextSize(20);
            domicilios.setAllCaps(false);
            linearLayout.addView(domicilios);
        }

    }
}
