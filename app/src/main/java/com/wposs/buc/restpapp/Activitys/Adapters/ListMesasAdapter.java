package com.wposs.buc.restpapp.Activitys.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.wposs.buc.restpapp.BD.Model.Mesas;
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

        Button btn = listItemView.findViewById(R.id.btnMesas);
        btn.setText(mesas.getName());
        switch (mesas.getStatus()){
            case "disponible":
                btn.setBackgroundResource(R.drawable.button_verde);
            case "ocupada":
                btn.setBackgroundResource(R.drawable.button_rojo);
        }

        return listItemView;
    }
}
