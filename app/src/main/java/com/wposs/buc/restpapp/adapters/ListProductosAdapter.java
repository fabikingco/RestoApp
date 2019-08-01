package com.wposs.buc.restpapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.model.Productos;

import java.util.ArrayList;

public class ListProductosAdapter extends ArrayAdapter<Productos> {

    public ListProductosAdapter(Activity activity, ArrayList<Productos> objects) {
        super(activity, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_produc, parent, false);
        }

        Productos productos = getItem(position);

        TextView tvTitulo = listItemView.findViewById(R.id.tvTitulo);
        TextView tvDescripcion = listItemView.findViewById(R.id.tvDescripcion);
        TextView tvValor = listItemView.findViewById(R.id.tvValor);


        tvTitulo.setText(productos.getNombre());
        tvDescripcion.setText(productos.getDescripcion());
        tvValor.setText(productos.getValor());

        return listItemView;
    }
}
