package com.wposs.buc.restpapp.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.wposs.buc.restpapp.activitys.tools.GlideApp;
import com.wposs.buc.restpapp.activitys.tools.ImagePickerActivity;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.Tools;
import com.wposs.buc.restpapp.model.Usuarios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class NuevoProductoActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;

    EditText etProducto, etValor, etDescripcion;
    EditText etCategoria;
    ImageView imgProducto;

    Spinner spinnerCategorias;
    String producto, categoria, descripcion;
    String valor;
    ArrayList<String> cateList;

    private FirebaseFirestore firestore;
    private FirebaseStorage mStorage;
    private StorageReference mProfileStorageReference;

    private Uri uriPhotoCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();
        mStorage = FirebaseStorage.getInstance();
        mProfileStorageReference = mStorage.getReference().child("products");
        cateList = llenarSpinner();
    }

    private ArrayList<String> llenarSpinner() {
        final ArrayList<String> cateList = new ArrayList<String>();

        firestore.collection("Categorias")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                cateList.add(document.getString("name"));
                                Log.d("Obtener Categorias", document.getId() + " => " + document.getData());
                            }
                            cargarWidgets();
                        } else {
                            Log.d("Obtener Categorias", "Error getting documents: ", task.getException());
                        }
                    }
                });

        return cateList;
    }

    private void cargarWidgets() {
        setContentView(R.layout.activity_nuevo_producto);

        etProducto = findViewById(R.id.etProducto);
        etValor = findViewById(R.id.etValor);
        etDescripcion = findViewById(R.id.etDescripcion);
        spinnerCategorias = findViewById(R.id.spinnerCategorias);
        imgProducto = findViewById(R.id.imgProducto);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, cateList);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerCategorias.setAdapter(adapter);

        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoria = (String) parent.getItemAtPosition(position);
                Toast.makeText(NuevoProductoActivity.this, "Selecciono -> " + categoria, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void nuevaCategoria(View view2) {

        /*AlertDialog.Builder builder = new AlertDialog.Builder(NuevoProductoActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_1_edit_text, null);

        builder.setView(v);
        final AlertDialog dialog = builder.show();

        Button btnAceptar = v.findViewById(R.id.btnAgregar);
        TextView btnCancelar = v.findViewById(R.id.btnCancelar);
        final EditText etCategoria = v.findViewById(R.id.etCategoria);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                String cat = etCategoria.getText().toString();
                ArrayList<String> categorias = bd.getAllCategorias();
                boolean existe = false;
                for(int i = 0; i < categorias.size(); i++){
                    if (cat.trim().equalsIgnoreCase(categorias.get(i)))
                        existe = true;
                }
                if (!existe) {
                    bd.crearCategoria(cat);
                }else{
                    Toast.makeText(NuevoProductoActivity.this, "Categoria ya existe", Toast.LENGTH_SHORT).show();
                }
                llenarSpinner();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });*/
    }

    public void crearProducto(View view) {
        if(!categoria.equals("NO EXISTEN CATEGORIAS")) {

            producto = etProducto.getText().toString();
            if (producto.isEmpty()) {
                Toast.makeText(this, "Nombre del pruducto esta vacio", Toast.LENGTH_SHORT).show();
                etProducto.setBackgroundResource(R.drawable.edittext_pichi_error);
                return;
            }

            if (etValor.getText().toString().isEmpty()) {
                Toast.makeText(this, "Valor del producto esta vacio", Toast.LENGTH_SHORT).show();
                etValor.setBackgroundResource(R.drawable.edittext_pichi_error);
                return;
            }
            valor = etValor.getText().toString();

            descripcion = etDescripcion.getText().toString();
            if (descripcion.isEmpty()) {
                Toast.makeText(this, "Descripcion del pruducto esta vacio", Toast.LENGTH_SHORT).show();
                etDescripcion.setBackgroundResource(R.drawable.edittext_pichi_error);
                return;
            }

            final StorageReference photoRef =
                    mProfileStorageReference.child(producto + ".jpg");

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
                            String id = UUID.randomUUID().toString();
                            Map<String,Object> todo = new HashMap<>();
                            todo.put("id", id);
                            todo.put("categoria", categoria);
                            todo.put("descripcion", descripcion);
                            todo.put("titulo", producto);
                            todo.put("valor", valor);
                            todo.put("url", downloadUrl.toString());
                            firestore.collection("Productos").document(id)
                                    .set(todo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Refresh data
                                    Toast.makeText(NuevoProductoActivity.this, "Producto creado con exito", Toast.LENGTH_SHORT).show();
                                    Tools.startView(NuevoProductoActivity.this, MainActivity.class);
                                    finish();
                                }
                            });
                        } else {
                            Log.d("Creacion de Cuenta", "No fue posible crear la cuenta " + task.getException().toString());
                        }
                    }
                }
            });

        } else {
            Toast.makeText(this, "Debe crear una nueva categoria", Toast.LENGTH_SHORT).show();
        }
    }

    public void agregarFotoProducto(View view) {
        Toast.makeText(this, "Aun no es posible agregar foto de producto", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                .into(imgProducto);
        imgProducto.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
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
