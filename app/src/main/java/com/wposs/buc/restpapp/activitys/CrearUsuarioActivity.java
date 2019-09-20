package com.wposs.buc.restpapp.activitys;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.wposs.buc.restpapp.activitys.tools.GlideApp;
import com.wposs.buc.restpapp.activitys.tools.ImagePickerActivity;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.model.Usuarios;
import java.io.IOException;
import java.util.List;

public class CrearUsuarioActivity extends AppCompatActivity {

    EditText etDocumento, etNombre, etApellido;
    CircularImageView imgProfile;
    RadioButton rbAdmin, rbCaja, rbMesero;

    public static final int REQUEST_IMAGE = 100;
    private static final String TAG = MainActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private FirebaseStorage mStorage;
    private StorageReference mProfileStorageReference;

    private Uri uriPhotoCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mStorage = FirebaseStorage.getInstance();
        mProfileStorageReference = mStorage.getReference().child("profile");

        Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("Crear usuarios");
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        etDocumento = findViewById(R.id.etDocumento);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        rbAdmin = findViewById(R.id.rbAdmin);
        rbCaja = findViewById(R.id.rbCaja);
        rbMesero = findViewById(R.id.rbMesero);
        imgProfile = findViewById(R.id.imgProfile);
    }

    public void cancelarCrearUsuario(View view) {
    }

    public void aceptarCrearUsuario(View view) {
        String sNombre = etNombre.getText().toString();
        String sApellido = etApellido.getText().toString();
        String sDocumento = etDocumento.getText().toString();

        if (!sNombre.isEmpty()) {
            if (!sApellido.isEmpty()) {
                if (!sDocumento.isEmpty()) {
                    if (verificarRadio()) {
                        String user = obtenerUsuario(sNombre, sApellido);
                        if (verificarUsuario(user)) {
                            String pass = crearPassword(sDocumento);
                            String name = sNombre +
                                    " " +
                                    sApellido;
                            String role = obtenerRole();
                            String status = "new";
                            createUser(user, pass, name, role, status, sDocumento);
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
        return sDocumento.substring(len - 6, len);
    }

    private boolean verificarUsuario(String user) {
        boolean ret = true;
        /**
         * Aqui agregar verificacion de usuarios para no crear usuarios repetidos.
         */
        return ret;
    }

    private boolean verificarIdUser(int id) {
        boolean ret = true;
        /**
         * Aqui verificar si documento ya esta registrado
         */
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
        String primeraLetraDelNombre = sNombre.substring(0, 1).toLowerCase();
        String apellido;
        if (!sApellido.contains(" ")) {
            apellido = sApellido.trim().toLowerCase();
        } else {
            apellido = sApellido.trim().substring(0, sApellido.indexOf(" ")).toLowerCase();
        }

        ret = primeraLetraDelNombre +
                apellido + "@restoapp.com.co";

        return ret;
    }

    public void agregarFotoUsuario(View view) {
        Toast.makeText(this, "Aun no es posible agregar foto de usuario", Toast.LENGTH_SHORT).show();
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void createUser(final String email, final String password, final String name, final String role,
                            final String status, final String id) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final StorageReference photoRef =
                            mProfileStorageReference.child(id + ".jpg");

                    UploadTask uploadTask = photoRef.putFile(uriPhotoCapture);

                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return photoRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUrl = task.getResult();
                                Log.i("The URL : ", downloadUrl.toString());
                                if (task.isSuccessful()) {
                                    Usuarios usuarios = new Usuarios();
                                    usuarios.setUser(email);
                                    usuarios.setPass(password);
                                    usuarios.setName(name);
                                    usuarios.setRole(role);
                                    usuarios.setStatus(status);
                                    usuarios.setPhotoUrl(downloadUrl.toString());
                                    usuarios.setId(id);
                                    mFirestore.collection("usuarios").document(email)
                                            .set(usuarios).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("Creacion de Cuenta", "Documento del usuario creado correctamente");
                                            } else {
                                                Log.e("Creacion de Cuenta", "Fallo creacion del documento del usuario ");
                                            }
                                        }
                                    });
                                    Log.d("Creacion de Cuenta", "Creada exitosamente con email " + email);
                                    Toast.makeText(CrearUsuarioActivity.this, "Nuevo usuario creado con exito. " + email + " y contraseña" + password, Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Log.d("Creacion de Cuenta", "No fue posible crear la cuenta " + task.getException().toString());
                                }
                            }
                        }
                    });

                }
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                uriPhotoCapture = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriPhotoCapture);

                    // loading profile image from local cache
                    loadProfile(uriPhotoCapture.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);

        GlideApp.with(this).load(url)
                .into(imgProfile);
        imgProfile.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }


    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Grant Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
}
