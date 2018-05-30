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

import Logic.Controlador;
import Logic.Usuario;
import Persistence.Persistencia;


public class Usuarios_Modifica_Borra_Fragment extends Fragment {
    EditText editableNombre,editablePassword;
    TextView email;
    Button btnguardar;
    Button btneliminar;
    Spinner spinner;
    Usuario usuario;
    Controlador controlador;

    public Usuarios_Modifica_Borra_Fragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_modifica__borra_usuario, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Modificar usuario");
        controlador=Controlador.getInstance();
        usuario = controlador.getUsuarioSelecionado();
        editableNombre = (EditText)getActivity().findViewById(R.id.editText_fragment_nombre);
        editablePassword = (EditText)getActivity().findViewById(R.id.editText_fragment_password);
        btnguardar = (Button)getActivity().findViewById(R.id.btn_fragment_guardar);
        btneliminar = (Button)getActivity().findViewById(R.id.btn_fragment_eliminar);
        email = (TextView)getActivity().findViewById(R.id.textview_email);
        spinner = (Spinner)getActivity().findViewById(R.id.spinner_rol);
        editableNombre.setText(usuario.getNombre());
        editablePassword.setText(usuario.getPassword());
        email.setText(usuario.getEmail());
        String[] roles = {"Admin","Tecnico","Empleado"};
        spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, roles));
        String myString = usuario.getRol(); //the value you want the position for
        ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter(); //cast to an ArrayAdapter
        int spinnerPosition = myAdap.getPosition(myString);
        spinner.setSelection(spinnerPosition);
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setNombre(editableNombre.getText().toString());
                usuario.setPassword(editablePassword.getText().toString());
                usuario.setRol((String) spinner.getSelectedItem());
                controlador.guardaUsuario(usuario);
                getFragmentManager().popBackStack();

            }
        });

        btneliminar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(getContext())
                    .setMessage("¿Está seguro de que desea eliminar este usuario?")
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            controlador.eliminarUsuario();
                            getFragmentManager().popBackStack();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    });
        ((Principal_Activity)getActivity()).hideFab();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
