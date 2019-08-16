package com.wposs.buc.restpapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wposs.buc.restpapp.model.Mesas;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.model.Productos;

import java.util.ArrayList;

public class ListMesasAdapter extends RecyclerView.Adapter<ListMesasAdapter.MesasViewHolder> {

    ArrayList<Mesas> mesas;
    Context context;

    private OnItemClickListenerMesas listener;

    /**
     * Interfaz de comunicación
     */
    public interface OnItemClickListenerMesas {
        void onItemClick(RecyclerView.ViewHolder item, int position, int id);
    }

    public void setOnItemClickListener(OnItemClickListenerMesas listener) {
        this.listener = listener;
    }

    public OnItemClickListenerMesas getOnItemClickListener() {
        return listener;
    }

    public ListMesasAdapter(ArrayList<Mesas> mesas, Context context) {
        this.mesas = mesas;
        this.context = context;
    }

    @NonNull
    @Override
    public MesasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_mesas, parent, false);
        return new MesasViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MesasViewHolder holder, int position) {
        Mesas mesas = this.mesas.get(position);
        holder.tvTitle.setText(mesas.getName());
        switch (mesas.getStatus()){
            case "disponible":
                holder.imgStatus.setImageResource(R.drawable.ic_check_circle_black_24dp);
                break;
            case "ocupada":
                holder.imgStatus.setImageResource(R.drawable.ic_info_black_24dp);
                break;
            case "cerrada":
                holder.imgStatus.setImageResource(R.drawable.ic_block_black_24dp);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mesas.size();
    }

    /*public ListMesasAdapter(Activity activity, ArrayList<Mesas> objects) {
        super(activity, 0, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_mesas, parent, false);
        }

        Mesas mesas = getItem(position);

        TextView tvTitle = listItemView.findViewById(R.id.tvTitle);
        TextView tvStatus = listItemView.findViewById(R.id.tvStatus);
        ImageView imgStatus = listItemView.findViewById(R.id.imgStatus);

        tvTitle.setText(mesas.getName());

        tvStatus.setText(mesas.getStatus());

        switch (mesas.getStatus()){
            case "disponible":
                imgStatus.setImageResource(R.drawable.ic_check_circle_black_24dp);
                break;
            case "ocupada":
                imgStatus.setImageResource(R.drawable.ic_info_black_24dp);
                break;
            case "cerrada":
                imgStatus.setImageResource(R.drawable.ic_block_black_24dp);
                break;
        }

        return listItemView;
    }*/

    public static class MesasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvStatus;
        ImageView imgStatus;
        RelativeLayout relative;

        ListMesasAdapter adapter = null;

        public MesasViewHolder(@NonNull View itemView, ListMesasAdapter adapter) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.adapter = adapter;
            tvTitle = itemView.findViewById(R.id.tvTitulo);
            imgStatus = itemView.findViewById(R.id.imgStatus);
            relative = itemView.findViewById(R.id.relative);
            relative.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final OnItemClickListenerMesas listener = adapter.getOnItemClickListener();
            int id = view.getId();
            if (listener != null) {
                listener.onItemClick(this, getAdapterPosition(), id);
            }

        }
    }
}
