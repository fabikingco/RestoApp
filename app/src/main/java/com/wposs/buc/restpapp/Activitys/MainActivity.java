package com.wposs.buc.restpapp.Activitys;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wposs.buc.restpapp.BD.Controler.ClsConexion;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.Tools;

import static com.wposs.buc.restpapp.Activitys.LoginActivity.usuario;

public class MainActivity extends AppCompatActivity {

    ClsConexion bd;
    TextView tvUsuario;

    private static final String TAG = "MainActivity";
    private static final int SMS_PERMISSION_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bd = new ClsConexion(this);
        tvUsuario = findViewById(R.id.tvUsuario);
        bannerUsuario();
        solicitarPermisos();
    }

    public void crearPedido(View view) {
        Tools.startView(this, CrearPedidoActivity.class);
    }

    public void finalizarPedido(View view) {
        Toast.makeText(this, R.string.opcionNoDisponible, Toast.LENGTH_SHORT).show();
    }

    public void configurarRestaurante(View view) {
        if (usuario.getRole().equals("Admin")) {
            Tools.startView(this, ConfigurarRestauranteActivity.class);
        } else {
            Toast.makeText(this, "No tienes permisos para ingresar a este menu", Toast.LENGTH_SHORT).show();
        }
    }

    private void bannerUsuario() {
        String banner = "Hola, " + usuario.getName() + " - " + usuario.getRole();
        tvUsuario.setText(banner);
    }

    private void solicitarPermisos() {
       if (!isSmsPermissionGranted()) {
           showRequestPermissionsInfoAlertDialog();
       }
    }

    private void showRequestPermissionsInfoAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Solicitud de permisos de SMS");
        builder.setMessage("Esta aplicacion tiene una funcion que permite enviar mensajes de texto. Acepte los siguientes permisos para usar esta funcion. ");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                requestReadAndSendSmsPermission();
            }
        });
        builder.show();
    }

    public boolean isSmsPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            Log.d(TAG, "shouldShowRequestPermissionRationale(), no permission requested");
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, SMS_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case SMS_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {


                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
