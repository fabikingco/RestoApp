package com.wposs.buc.restpapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.fragment.app.Fragment;

import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.Tools;
import com.wposs.buc.restpapp.activitys.CrearUsuarioActivity;
import com.wposs.buc.restpapp.activitys.VisualizarUsuariosActivity;

public class AccountFragment extends Fragment {

    Button btnAgragarNuevoProducto, btnVerProduc;

    public AccountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        btnAgragarNuevoProducto = view.findViewById(R.id.btnAgragarNuevoProducto);
        btnVerProduc = view.findViewById(R.id.btnVerProduc);

        btnAgragarNuevoProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.startView(getActivity(), CrearUsuarioActivity.class, false);
            }
        });

        btnVerProduc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.startView(getActivity(), VisualizarUsuariosActivity.class, false);
            }
        });
        return view;
    }
}
