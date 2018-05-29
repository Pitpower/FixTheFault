package com.example.power.fixthefault;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Logic.Averia;
import Logic.Controlador;
import Logic.Session;
import Logic.Usuario;
import Persistence.Persistencia;


public class Usuario_Registrar_Fragment extends Fragment {
    EditText editextNombre,editextEmail,editextPassword;
    Spinner spinner;
    Button guardar;
    Controlador controlador;

    private OnFragmentInteractionListener mListener;

    public Usuario_Registrar_Fragment() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Nuevo usuario");
        controlador = Controlador.getInstance();
        guardar = (Button)getView().findViewById(R.id.btn_fragment_nueva_guardar);
        editextNombre = (EditText)getView().findViewById(R.id.editext_fragment_nueva_nombre);
        editextEmail = (EditText)getView().findViewById(R.id.editext_fragment_nueva_email);
        editextPassword = (EditText)getView().findViewById(R.id.editText_password);
        spinner = (Spinner)getView().findViewById(R.id.spinner_usuario);
        String[] roles = {"Admin","Técnico","Empleado"};
        spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, roles));

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editextNombre.getText().toString();
                String email = editextEmail.getText().toString();
                String password = editextPassword.getText().toString();
                String rol = (String)spinner.getSelectedItem();


                Usuario user = new Usuario(nombre,email,rol,password);
                controlador.registrarUsuario(user);

                getFragmentManager().popBackStack();
            }
        });
        ((Main2Activity)getActivity()).hideFab();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registrar_usuario_fragment, container, false);
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
