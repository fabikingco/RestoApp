package com.wposs.buc.restpapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.Tools;
import com.wposs.buc.restpapp.activitys.ConfigurarMesasActivity;
import com.wposs.buc.restpapp.activitys.EditarCategoriasActivity;
import com.wposs.buc.restpapp.activitys.NuevoProductoActivity;
import com.wposs.buc.restpapp.activitys.VisualizarProductosActivity;

public class SettingsFragment extends Fragment {

    Button btnAgragarNuevoProducto, btnVerProduc, btnEditarCategorias, btnCrearMesas;

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        btnAgragarNuevoProducto = view.findViewById(R.id.btnAgragarNuevoProducto);
        btnVerProduc = view.findViewById(R.id.btnVerProduc);
        btnEditarCategorias = view.findViewById(R.id.btnEditarCategorias);
        btnCrearMesas = view.findViewById(R.id.btnCrearMesas);

        btnAgragarNuevoProducto.setOnClickListener(view1 -> {
            Tools.startView(getActivity(), NuevoProductoActivity.class, false);
        });
        btnVerProduc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.startView(getActivity(), EditarCategoriasActivity.class, false);
            }
        });
        btnEditarCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.startView(getActivity(), VisualizarProductosActivity.class, false);
            }
        });

        btnCrearMesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.startView(getActivity(), ConfigurarMesasActivity.class, false);
            }
        });

        return view;
    }
}
