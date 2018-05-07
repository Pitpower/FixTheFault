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
import Logic.Session;
import Logic.Usuario;
import Persistence.Persistencia;


public class RegistrarUsuario_fragment extends Fragment {
    TextView textviewLugar,textviewDescripcion;
    EditText editextNombre,editextEmail,editextPassword;
    Spinner spinner;
    Button guardar;
    Session sesion;
    Persistencia persistencia;



    private OnFragmentInteractionListener mListener;

    public RegistrarUsuario_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Nuevo usuario");
        sesion= Session.getInstance();
        persistencia=Persistencia.getInstance();
        Log.i("Usuario",sesion.getUser().getNombre());
        //textviewLugar=(TextView)getView().findViewById(R.id.textview_fragment_nueva_lugar);
       // textviewDescripcion=(TextView)getView().findViewById(R.id.textview_fragment_nueva_descripcion);
        guardar=(Button)getView().findViewById(R.id.btn_fragment_nueva_guardar);
        editextNombre=(EditText)getView().findViewById(R.id.editext_fragment_nueva_nombre);
        editextEmail=(EditText)getView().findViewById(R.id.editext_fragment_nueva_email);
        editextPassword=(EditText)getView().findViewById(R.id.editText_password);
        spinner=(Spinner)getView().findViewById(R.id.spinner_usuario);
        String[] roles={"Admin","tecnico","normal"};
        spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, roles));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("averias");


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre=editextNombre.getText().toString();
                String email=editextEmail.getText().toString();
                String password=editextPassword.getText().toString();
                String rol=(String)spinner.getSelectedItem();


                Usuario user=new Usuario(nombre,email,rol,password);
                persistencia.registrarUsuario(user);
               // Log.i("Usuario",sesion.getUser().getNombre());

                getFragmentManager().popBackStack();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.registrar_usuario_fragment, container, false);
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
