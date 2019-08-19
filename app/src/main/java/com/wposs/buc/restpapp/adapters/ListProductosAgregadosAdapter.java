package com.wposs.buc.restpapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.model.ProductosAgregadosPedido;

import java.util.ArrayList;

public class ListProductosAgregadosAdapter extends ArrayAdapter<ProductosAgregadosPedido> {


    public ListProductosAgregadosAdapter(Activity activity, ArrayList<ProductosAgregadosPedido> objects) {
        super(activity, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_productos_btn_sheet, parent, false);
        }

        ProductosAgregadosPedido pedido = getItem(position);

        TextView tvTituo = listItemView.findViewById(R.id.tvTitle);

        String mensaje = pedido.getCantidad() + ": " + pedido.getName();

        tvTituo.setText(mensaje);

        return listItemView;
    }
}
