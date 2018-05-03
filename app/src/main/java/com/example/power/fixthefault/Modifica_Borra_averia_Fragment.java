package com.example.power.fixthefault;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import Logic.Averia;
import Persistence.Persistencia;


public class Modifica_Borra_averia_Fragment extends Fragment {
    String key;
    String lugar;
    String descripcion;
    TextView etiquetaDescripcion,etiquetaLugar;
    EditText editableLugar,editableDescripcion;
    Button btnguardar;
    Button btneliminar;
    Persistencia persistencia;

    public Modifica_Borra_averia_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        key = getArguments().getString("Key");
        lugar = getArguments().getString("Lugar");
        descripcion = getArguments().getString("Descripcion");
        persistencia = Persistencia.getInstance();
        return inflater.inflate(R.layout.fragment_modifica__borra_averia, container, false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        etiquetaLugar = (TextView)getActivity().findViewById(R.id.textview_fragment_lugar);
        etiquetaDescripcion = (TextView)getActivity().findViewById(R.id.textview_fragment_descripcion);
        editableLugar = (EditText)getActivity().findViewById(R.id.editText_fragment_nombre);
        editableDescripcion = (EditText)getActivity().findViewById(R.id.editText_fragment_Descripcion);
        btnguardar = (Button)getActivity().findViewById(R.id.btn_fragment_guardar);
        btneliminar = (Button)getActivity().findViewById(R.id.btn_fragment_eliminar);

        etiquetaLugar.setText("Lugar");
        etiquetaDescripcion.setText("Descripcion");
        editableLugar.setText(lugar);
        editableDescripcion.setText(descripcion);

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lugar = editableLugar.getText().toString();
                descripcion = editableDescripcion.getText().toString();
                Averia averia = new Averia(lugar,descripcion,null);
                persistencia.guardaAveriaModificada(key,averia);
                getFragmentManager().popBackStack();

            }
        });

    btneliminar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            persistencia.eliminaAveria(key);
            getFragmentManager().popBackStack();
        }
    });

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
