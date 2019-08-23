package com.wposs.buc.restpapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    /**
     * Interfaz de comunicación
     */
    public interface OnItemClickListener {
        void onItemClickAgregados(RecyclerView.ViewHolder item, int position, int id);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return listener;
    }


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
        holder.tvTitle.setText(pedidos.getCantidad() + ". " + pedidos.getName());

    }

    @Override
    public int getItemCount() {
        return productosPedidoActivo.size();
    }


    public static class PedidoAgregadoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ListProductosAgregadosAdapter adapter;

        TextView tvTitle;
        LinearLayout linearEliminar;
        ImageView imgStatus;
        


        public PedidoAgregadoViewHolder(@NonNull View itemView, ListProductosAgregadosAdapter adapter) {
            super(itemView);
            this.adapter = adapter;

            tvTitle = itemView.findViewById(R.id.tvTitle);
            linearEliminar = itemView.findViewById(R.id.linearEliminar);
            imgStatus = itemView.findViewById(R.id.imgStatus);

        }

        @Override
        public void onClick(View view) {
            final OnItemClickListener listener = adapter.getOnItemClickListener();
            int id = view.getId();
            if (listener != null) {
                listener.onItemClickAgregados(this, getAdapterPosition(), id);
            }
        }
    }
}