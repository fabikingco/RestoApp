package com.wposs.buc.restpapp.adapters;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.activitys.tools.GlideApp;
import com.wposs.buc.restpapp.model.Productos;
import java.util.ArrayList;

public class ListProductosPedidoAdapter extends RecyclerView.Adapter<ListProductosPedidoAdapter.PedidoViewHolder> {

    ArrayList<Productos> productos;
    Context context;

    /**
     * Interfaz de comunicación
     */
    public interface OnItemClickListener {
        void onItemClick(RecyclerView.ViewHolder item, int position, int id);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return listener;
    }


    public ListProductosPedidoAdapter(ArrayList<Productos> objects, Context context) {
        this.productos = objects;
        this.context = context;
    }


    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_productos, viewGroup, false);
        return new PedidoViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder pedidoViewHolder, final int i) {
        Productos producto = this.productos.get(i);
        pedidoViewHolder.tvTitulo.setText(producto.getNombre());
        pedidoViewHolder.tvValor.setText("$" + String.valueOf(producto.getValor()));
        GlideApp.with(context).load(producto.getPhotoUrl())
                .into(pedidoViewHolder.imgPhoto);
        pedidoViewHolder.imgPhoto.setColorFilter(ContextCompat.getColor(context, android.R.color.transparent));
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public static class PedidoViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        ImageView imgPhoto;
        TextView tvTitulo, tvValor;
        Button btnAgregar;

        ListProductosPedidoAdapter adapter = null;

        public PedidoViewHolder(@NonNull View itemView, ListProductosPedidoAdapter adapter) {
            super(itemView);

            itemView.setOnClickListener(this);

            this.adapter = adapter;

            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvValor = itemView.findViewById(R.id.tvValor);
            btnAgregar = itemView.findViewById(R.id.btnAgregar);
            btnAgregar.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            final OnItemClickListener listener = adapter.getOnItemClickListener();
            int id = v.getId();
            if (listener != null) {
                listener.onItemClick(this, getAdapterPosition(), id);
            }
        }


    }

}
