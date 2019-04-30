package com.wposs.buc.restpapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wposs.buc.restpapp.R;


public class SeleccionProductoFragment extends Fragment {


    public SeleccionProductoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_seleccion_producto, container, false);
        return v;
    }



}
