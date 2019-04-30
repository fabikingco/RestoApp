package com.wposs.buc.restpapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wposs.buc.restpapp.bd.model.Usuarios;
import com.wposs.buc.restpapp.R;

import java.util.ArrayList;

public class ListUsuariosAdapter extends ArrayAdapter<Usuarios> {

    public ListUsuariosAdapter(Activity context, ArrayList<Usuarios> listItems) {
        super(context, 0, listItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_usuarios, parent, false);
        }

        Usuarios usuarios = getItem(position);

        TextView tv1 = (TextView) listItemView.findViewById(R.id.tv1);

        tv1.setText(usuarios.getUser());

        TextView tv2 = (TextView) listItemView.findViewById(R.id.tv2);

        tv2.setText(usuarios.getRole());

        ImageView iconView = (ImageView) listItemView.findViewById(R.id.list_item_icon);

        iconView.setImageResource(R.mipmap.ic_account);

        return listItemView;
    }

}
