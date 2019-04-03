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

import com.wposs.buc.restpapp.R;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pedido);

        sharedPreferences = getSharedPreferences("configuracionRestaurante", Context.MODE_PRIVATE);
        String numeroMesas = sharedPreferences.getString("mesas", "0");
        boolean infoDomicilios = sharedPreferences.getBoolean("domicilios", false);

        if (!numeroMesas.equals("0")) {
            crearBotonesDeMesas(numeroMesas, infoDomicilios);
        } else {
            Toast.makeText(this, "Cree mesas en el menu de configuracion de mesas", Toast.LENGTH_SHORT).show();
        }
    }

    private void crearBotonesDeMesas(String numeroMesas, boolean infoDomicilios) {

        LinearLayout linearLayout = findViewById(R.id.linearBotones);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 120);
        lp.setMargins(0, 10, 0, 0);

        int numMesas = Integer.parseInt(numeroMesas);

        Button[] buttons = new Button[numMesas];

        for (int i = 0; i < numMesas; i++){
            int mesa = i+1;
            buttons[i] = new Button(this);
            buttons[i].setBackgroundResource(R.drawable.button_color);
            buttons[i].setLayoutParams(lp);
            buttons[i].setText("MESA numero " + mesa);
            buttons[i].setTextColor(Color.parseColor("#0F265C"));
            buttons[i].setTextSize(22);
            buttons[i].setAllCaps(false);
            linearLayout.addView(buttons[i]);
        }

    }
}
