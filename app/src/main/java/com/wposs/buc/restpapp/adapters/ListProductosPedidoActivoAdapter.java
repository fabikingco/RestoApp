package com.wposs.buc.restpapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.model.ProductosPedidoActivo;

import java.util.ArrayList;

public class ListProductosPedidoActivoAdapter extends RecyclerView.Adapter<ListProductosPedidoActivoAdapter.PedidoActivoViewHolder> {

    private ArrayList<ProductosPedidoActivo> productosPedidoActivo;
    private Context context;

    public ListProductosPedidoActivoAdapter(ArrayList<ProductosPedidoActivo> ProductosPedidoActivo, Context context) {
        this.productosPedidoActivo = ProductosPedidoActivo;
        this.context = context;
    }

    @NonNull
    @Override
    public PedidoActivoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_productos_pedido_activo, parent, false);
        return new PedidoActivoViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ListProductosPedidoActivoAdapter.PedidoActivoViewHolder holder, int position) {
        ProductosPedidoActivo pedidos= this.productosPedidoActivo.get(position);
        holder.tvName.setText(pedidos.getName());
        holder.tvCantidad.setText("Cantidad: " + pedidos.getCantidad());
        holder.tvValor.setText("$" + pedidos.getValorTotal());
    }

    @Override
    public int getItemCount() {
        return productosPedidoActivo.size();
    }



    public static class PedidoActivoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgProducto;
        TextView tvName, tvValor, tvCantidad;
        ListProductosPedidoActivoAdapter adapter;


        public PedidoActivoViewHolder(@NonNull View itemView, ListProductosPedidoActivoAdapter adapter) {
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
