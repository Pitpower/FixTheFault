package com.example.power.fixthefault;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Locale;

import Logic.AdapterSpinner;
import Logic.Averia;
import Logic.Session;
import Persistence.Persistencia;


public class Modifica_Borra_averia_Fragment extends Fragment {
    String key;
    String lugar;
    String descripcion;
    TextView etiquetaCreador;
    TextView creadorFijo;
    EditText editableLugar,editableDescripcion;
    Button btnguardar;
    Button btneliminar;
    Button btnprioridad;
    Persistencia persistencia;
    Averia averia;
    Spinner spinner;
    Spinner spinnerPrioridad;
    Session sesion;

    public Modifica_Borra_averia_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        persistencia = Persistencia.getInstance();
        averia = persistencia.getAveriaToModificar();
        key = persistencia.getKeyAveria();
        lugar = averia.getUbicacion();
        descripcion = averia.getDescripcion();
        return inflater.inflate(R.layout.fragment_modifica__borra_averia, container, false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Modificar averia");
        etiquetaCreador = (TextView)getActivity().findViewById(R.id.textView_creador);
        editableLugar = (EditText)getActivity().findViewById(R.id.editText_fragment_nombre);
        editableDescripcion = (EditText)getActivity().findViewById(R.id.editText_fragment_Descripcion);
        btnguardar = (Button)getActivity().findViewById(R.id.btn_fragment_guardar);
        btneliminar = (Button)getActivity().findViewById(R.id.btn_fragment_eliminar);
        spinnerPrioridad=spinner = (Spinner)getActivity().findViewById(R.id.spinnerPrioridad);
        spinner = (Spinner)getActivity().findViewById(R.id.spinnerEstado);
       sesion=Session.getInstance();
        if(!sesion.getUser().getRol().equals("Admin")){
            spinnerPrioridad.setEnabled(false);}
        spinnerPrioridad.setAdapter(new AdapterSpinner(getActivity()));


        String[] estados = {"En cola","En ejecución","Pausa","Terminada"};
        if(sesion.getUser().getRol().equals("normal")){
            spinner.setEnabled(false);}
        spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, estados));
       String estado= averia.getEstado();
       int estadoInt=3;
       if(estado.equals("En cola"))
           estadoInt=0;
            else if (estado.equals("En ejecución"))
                estadoInt=1;
                else if (estado.equals("Pausa"))
                    estadoInt=2;
                    else
                        estadoInt=3;
        spinner.setSelection(estadoInt);
        spinnerPrioridad.setSelection(averia.getPrioridad());
        editableLugar.setText(lugar);
        editableDescripcion.setText(descripcion);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        etiquetaCreador.setText(averia.getUser().getNombre()+" el "+dateFormat.format(averia.getFechaCreacion()));

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lugar = editableLugar.getText().toString();
                descripcion = editableDescripcion.getText().toString();
                String estado = (String)spinner.getSelectedItem();
                int prioridad = (Integer)spinnerPrioridad.getSelectedItemPosition();
                averia.setUbicacion(lugar);
                averia.setDescripcion(descripcion);
                averia.setEstado(estado);
                averia.setPrioridad(prioridad);
                persistencia.guardaAveriaModificada(averia);
                getFragmentManager().popBackStack();

            }
        });

    btneliminar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(getContext())
                    .setMessage("¿Está seguro de que desea eliminar esta avería?")
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            persistencia.eliminaAveria(key);
                            getFragmentManager().popBackStack();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

        }
    });
        ((Main2Activity)getActivity()).hideFab();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
