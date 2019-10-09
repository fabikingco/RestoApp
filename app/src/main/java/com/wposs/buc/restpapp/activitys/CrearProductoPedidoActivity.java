package com.wposs.buc.restpapp.activitys;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wposs.buc.restpapp.Tools;
import com.wposs.buc.restpapp.adapters.ListProductosAgregadosAdapter;
import com.wposs.buc.restpapp.adapters.ListProductosPedidoAdapter;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.bd.controler.ClsConexion;
import com.wposs.buc.restpapp.model.PedidosActivos;
import com.wposs.buc.restpapp.model.Productos;
import com.wposs.buc.restpapp.model.ProductosPedidoActivo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class CrearProductoPedidoActivity extends AppCompatActivity implements ListProductosPedidoAdapter.OnItemClickListener, ListProductosAgregadosAdapter.OnItemClickListener{

    //Variables
    int numeros = 0;
    int total = 0;
    int cantidadTotal = 0;
    int valorTotal = 0;
    String mesa;


    //BaseDeDatos
    FirebaseFirestore firestore;
    ArrayList<ProductosPedidoActivo> productoAgregado;
    ArrayList<Productos> productos = null;
    ClsConexion sqlite;

    //Widgets
    Button btnCrearPedido;
    RecyclerView rvProductosAgregados;
    TextView tvNumeros , tvTotal;
    RecyclerView recyclerView;
    BottomSheetBehavior sheetBehavior;
    LinearLayoutCompat layoutBottomSheet;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pedido_producto);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            mesa = bundle.getString("mesa", "MesaNoEncontrada");
        }

        ActionBar toolbar;
        toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setTitle("Pedido para mesa " + mesa);
        }

        findView();

        productoAgregado = new ArrayList<>();
        btnCrearPedido.setOnClickListener(onClickCrearPedido);

        rvProductosAgregados.setLayoutManager(new LinearLayoutManager(this));
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        tvNumeros.setText("" + numeros);
        tvTotal.setText("$" + total);
        layoutBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }
            @Override
            public void onSlide(@NonNull View view, float v) { }
        });

        firestore = FirebaseFirestore.getInstance();
        sqlite = new ClsConexion(this);
        productos = new ArrayList<>();

        GridLayoutManager glm = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(glm);

        firestore.collection("Productos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot doc : task.getResult()){
                            Productos prod = new Productos(doc.getString("id"),
                                    doc.getString("titulo"),
                                    doc.getString("valor"),
                                    doc.getString("categoria"),
                                    doc.getString("descripcion"),
                                    doc.getString("url"));
                            productos.add(prod);
                            ListProductosPedidoAdapter lpAdapter = new ListProductosPedidoAdapter(productos, getApplicationContext());
                            recyclerView.setAdapter(lpAdapter);
                            lpAdapter.setOnItemClickListener(CrearProductoPedidoActivity.this);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CrearProductoPedidoActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                Toast.makeText(CrearProductoPedidoActivity.this, "Lista actualizada", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void findView() {
        refreshLayout = findViewById(R.id.refreshLayout);
        btnCrearPedido = findViewById(R.id.btnCrearPedido);
        rvProductosAgregados = findViewById(R.id.rvProductosAgregados);
        tvNumeros = findViewById(R.id.tvNumeros);
        tvTotal = findViewById(R.id.tvTotal);
        layoutBottomSheet = findViewById(R.id.bottom_sheet);
        recyclerView = findViewById(R.id.rvItemsProductos);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder item, int position, int id) {
        Productos productos = this.productos.get(position);
        if(id == R.id.btnAgregar){
            Toast.makeText(this, "Agregando producto: " + productos.getNombre(), Toast.LENGTH_SHORT).show();
            int cantidad = 1;

            Iterator itr = productoAgregado.iterator();
            if (productoAgregado.size() != 0){
                while (itr.hasNext()){
                    ProductosPedidoActivo j = (ProductosPedidoActivo) itr.next();
                    if (productos.getNombre().equals(j.getName())){
                        cantidad = j.getCantidad() + 1;
                        itr.remove();
                        break;
                    }
                }
            }

           /* numeros ++;
            tvNumeros.setText("" + numeros);
            total += productos.getValor();
            tvTotal.setText("$" + total);*/
            //ProductosAgregadosPedido pedido = new ProductosAgregadosPedido(productos.getId(), productos.getNombre(), productos.getValor(), cantidad);
            int valorTotalProducto = cantidad * productos.getValor();
            ProductosPedidoActivo activo = new ProductosPedidoActivo(productos.getId(), productos.getNombre(), cantidad, productos.getValor(), valorTotalProducto, productos.getPhotoUrl());
            productoAgregado.add(activo);
            obtenerCantidadValorTotal();
            recargarRecyclerAgregados();
        }
    }


    /**
     * Aqui se realiza la eliminacion de los productos agregados.
     *
     * @param item
     * @param position
     * @param id
     */
    @Override
    public void onItemClickAgregados(RecyclerView.ViewHolder item, int position, int id) {
        ProductosPedidoActivo producto = productoAgregado.get(position);
        int cantidad = producto.getCantidad();
        Toast.makeText(this, "Cantidad = " + cantidad, Toast.LENGTH_SHORT).show();
        if (cantidad == 1){
            productoAgregado.remove(position);
        } else {
            productoAgregado.remove(position);
            cantidad--;
            producto.setCantidad(cantidad);
            productoAgregado.add(producto);
        }

        obtenerCantidadValorTotal();
        recargarRecyclerAgregados();

    }

    private void recargarRecyclerAgregados(){
        ListProductosAgregadosAdapter adapter = new ListProductosAgregadosAdapter(productoAgregado, getApplicationContext());
        rvProductosAgregados.setAdapter(adapter);
        adapter.setOnItemClickListener(CrearProductoPedidoActivity.this);
    }

    private void obtenerCantidadValorTotal(){
        int cantidadProducto;
        cantidadTotal = 0;
        valorTotal = 0;

        if (productoAgregado.size() != 0){
            Iterator itr = productoAgregado.iterator();
            while (itr.hasNext()){
                ProductosPedidoActivo j = (ProductosPedidoActivo) itr.next();
                cantidadProducto = j.getCantidad();
                cantidadTotal += cantidadProducto;
                valorTotal += (j.getValor() * cantidadProducto);
                /*if (productos.getNombre().equals(j.getName())){
                    cantidad = j.getCantidad() + 1;
                    itr.remove();
                    break;
                }*/
            }
            tvNumeros.setText("" +cantidadTotal);
            tvTotal.setText("$" + valorTotal);
        } else {
            tvNumeros.setText("" +cantidadTotal);
            tvTotal.setText("$0");
        }


    }

    /**
     * Boton para Crear el pedido.
     * 1. Validar que productoAgregado no este vacio
     * 2. Obtener los datos del mesero y mesa.
     * 3. Insertar PedidosActivos.java y  ProductosPedidoActivo.java
     */
    private Button.OnClickListener onClickCrearPedido
            = new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            if (productoAgregado.size() != 0){
                Toast.makeText(CrearProductoPedidoActivity.this, "Productos = " + cantidadTotal + " valor = " + valorTotal, Toast.LENGTH_SHORT).show();
                String id = armarIdPedido();
                Log.d("id-pedido", id);
                String imp = "0";
                String subTotal = "0";
                String total = "" + valorTotal;
                
                PedidosActivos pedido = new PedidosActivos();



            } else {
                Toast.makeText(CrearProductoPedidoActivity.this, "Agrega productos al carrito para crear pedidos", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private String armarIdPedido() {
        return mesa +
                "-" +
                Tools.DateToStr(new Date(), "ddMMyyyyHHmmss");
    }
}
