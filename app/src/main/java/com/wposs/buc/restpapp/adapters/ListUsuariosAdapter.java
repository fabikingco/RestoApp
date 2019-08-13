package com.wposs.buc.restpapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.wposs.buc.restpapp.activitys.tools.GlideApp;
import com.wposs.buc.restpapp.model.Usuarios;
import com.wposs.buc.restpapp.R;

import java.util.ArrayList;

public class ListUsuariosAdapter extends ArrayAdapter<Usuarios> {

    Context context;

    public ListUsuariosAdapter(Context context, ArrayList<Usuarios> listItems) {
        super(context, 0, listItems);
        this.context = context;
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

        GlideApp.with(context).load(usuarios.getPhotoUrl())
                .into(iconView);
        iconView.setColorFilter(ContextCompat.getColor(context, android.R.color.transparent));

        return listItemView;
    }

}
