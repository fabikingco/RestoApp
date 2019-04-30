package com.wposs.buc.restpapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wposs.buc.restpapp.bd.model.Mesas;
import com.wposs.buc.restpapp.R;

import java.util.ArrayList;

public class ListMesasAdapter extends ArrayAdapter<Mesas> {

    public ListMesasAdapter(Activity activity, ArrayList<Mesas> objects) {
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
    }
}
