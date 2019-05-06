package com.wposs.buc.restpapp.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.bd.model.Productos;
import java.util.ArrayList;

public class ListProductosPedidoAdapter extends RecyclerView.Adapter<ListProductosPedidoAdapter.PedidoViewHolder> {

    ArrayList<Productos> productos;


    public ListProductosPedidoAdapter(ArrayList<Productos> objects) {
        this.productos = objects;
    }


    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_productos, viewGroup, false);
        return new PedidoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder pedidoViewHolder, int i) {
        Productos producto = this.productos.get(i);
        pedidoViewHolder.tvTitulo.setText(producto.getNombre());
        pedidoViewHolder.tvValor.setText(String.valueOf(producto.getValor()));
        pedidoViewHolder.tvDescripcion.setText(producto.getDescripcion());

    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public static class PedidoViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhoto;
        TextView tvTitulo, tvValor, tvDescripcion;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvValor = itemView.findViewById(R.id.tvValor);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);


        }


    }

}
