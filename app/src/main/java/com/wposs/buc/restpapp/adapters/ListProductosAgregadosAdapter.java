package com.wposs.buc.restpapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.model.ProductosAgregadosPedido;
import com.wposs.buc.restpapp.model.ProductosPedidoActivo;

import java.util.ArrayList;

public class ListProductosAgregadosAdapter extends RecyclerView.Adapter<ListProductosAgregadosAdapter.PedidoAgregadoViewHolder> {

    private ArrayList<ProductosPedidoActivo> productosPedidoActivo;
    private Context context;

    public ListProductosAgregadosAdapter(ArrayList<ProductosPedidoActivo> ProductosPedidoActivo, Context context) {
        this.productosPedidoActivo = ProductosPedidoActivo;
        this.context = context;
    }

    @NonNull
    @Override
    public PedidoAgregadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_productos_btn_sheet, parent, false);
        return new PedidoAgregadoViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ListProductosAgregadosAdapter.PedidoAgregadoViewHolder holder, int position) {
        ProductosPedidoActivo pedidos = this.productosPedidoActivo.get(position);

    }

    @Override
    public int getItemCount() {
        return productosPedidoActivo.size();
    }


    public static class PedidoAgregadoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ListProductosAgregadosAdapter adapter;

        TextView tvTitle;
        


        public PedidoAgregadoViewHolder(@NonNull View itemView, ListProductosAgregadosAdapter adapter) {
            super(itemView);
            this.adapter = adapter;

            imgProducto = itemView.findViewById(R.id.imgProducto);
            tvName = itemView.findViewById(R.id.tvName);
            tvValor = itemView.findViewById(R.id.tvValor);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);


        }

        @Override
        public void onClick(View view) {

        }
    }
}