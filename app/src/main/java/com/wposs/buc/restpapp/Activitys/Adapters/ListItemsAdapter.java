package com.wposs.buc.restpapp.Activitys.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wposs.buc.restpapp.R;

import java.util.ArrayList;

public class ListItemsAdapter extends ArrayAdapter<ListItems> {

    private static final String LOG_TAG = ListItemsAdapter.class.getSimpleName();

    public ListItemsAdapter(Activity context, ArrayList<ListItems> listItems) {
        super(context, 0, listItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        ListItems listItems = getItem(position);

        TextView tv1 = (TextView) listItemView.findViewById(R.id.tv1);

        tv1.setText(listItems.getTextView1());

        TextView tv2 = (TextView) listItemView.findViewById(R.id.tv2);

        tv2.setText(listItems.getTextView2());

        ImageView iconView = (ImageView) listItemView.findViewById(R.id.list_item_icon);

        iconView.setImageResource(listItems.getImageResourceId());

        return listItemView;
    }
}
