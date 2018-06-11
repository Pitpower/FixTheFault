package com.example.power.fixthefault;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import Logic.Controlador;
import Logic.Session;


public class Averias_Modifica_Borra_Fragment extends Fragment {
    Controlador controlador;
    String key;
    String lugar;
    String descripcion;
    TextView etiquetaCreador;
    EditText editableLugar,editableDescripcion;
    Button btnguardar;
    Button btneliminar;
    TextView tecnicoAsignado;
    Averia averia;
    Spinner spinner;
    Spinner spinnerPrioridad;
    Session sesion;
    boolean esEmpleado;

    public Averias_Modifica_Borra_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        controlador = Controlador.getInstance();
        averia = controlador.getAveriaSeleccionada();
        key = controlador.getKeyAveria();
        lugar = averia.getUbicacion();
        descripcion = averia.getDescripcion();
        return inflater.inflate(R.layout.fragment_modifica__borra_averia, container, false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Modificar averia");
        etiquetaCreador = (TextView)getActivity().findViewById(R.id.textView_creador);
        tecnicoAsignado = (TextView)getActivity().findViewById(R.id.textviewTecnico);
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

        if (averia.getTecnico() != null){
            tecnicoAsignado.setText(averia.getTecnico().getNombre());
        }
        String[] estados = {"En cola","En ejecución","Pausa","Terminada"};
        if(sesion.getUser().getRol().equals("Empleado")){
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
        esEmpleado = false;
        if(controlador.getUsuarioLogeado().getRol().equals("Empleado")){
            if(!averia.getUser().getNombre().equals(controlador.getUsuarioLogeado().getNombre())){
                btnguardar.setText("Volver");
                esEmpleado = true;

            }
        }
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!esEmpleado){
                lugar = editableLugar.getText().toString();
                descripcion = editableDescripcion.getText().toString();
                String estado = (String)spinner.getSelectedItem();
                if (estado.equals("En ejecución")){averia.setTecnico(controlador.getUsuarioLogeado());}
                int prioridad = (Integer)spinnerPrioridad.getSelectedItemPosition();
                averia.setUbicacion(lugar);
                averia.setDescripcion(descripcion);
                averia.setEstado(estado);
                averia.setPrioridad(prioridad);
                controlador.guardaAveria(averia);}

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
                            controlador.eliminaAveria();
                            getFragmentManager().popBackStack();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

        }
    });
        ((Principal_Activity)getActivity()).hideFab();

    if(!controlador.getRolUsuario().equals("Admin")){
        btneliminar.setVisibility(view.INVISIBLE);
    }

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
