package com.example.power.fixthefault;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Logic.Averia;
import Logic.Controlador;
import Logic.Session;


public class Averia_Nueva_Fragment extends Fragment {
    TextView textviewLugar,textviewDescripcion;
    EditText editextLugar,editextDescripcion;
    Button guardar;
    Controlador controlador;

    private OnFragmentInteractionListener mListener;

    public Averia_Nueva_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Nueva avería");
        controlador=Controlador.getInstance();
        textviewLugar = (TextView)getView().findViewById(R.id.textview_fragment_nueva_lugar);
        textviewDescripcion = (TextView)getView().findViewById(R.id.textview_fragment_nueva_descripcion);
        guardar = (Button)getView().findViewById(R.id.btn_fragment_nueva_guardar);
        editextLugar = (EditText)getView().findViewById(R.id.editext_fragment_nueva_lugar);
        editextDescripcion = (EditText)getView().findViewById(R.id.editext_fragment_nueva_descripcion);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lugar = editextLugar.getText().toString();
                String descripcion = editextDescripcion.getText().toString();

                if(TextUtils.isEmpty(lugar) ||  TextUtils.isEmpty(descripcion)){
                    new AlertDialog.Builder(getContext())
                            .setMessage("Rellene todos los campos para añadir una avería")
                            .setPositiveButton("Vale", null)
                            .show();
                } else {
                    Averia averia = new Averia(editextLugar.getText().toString(), editextDescripcion.getText().toString(), controlador.getUsuarioLogeado());
                    controlador.guardaNuevaAveria(averia);
                    getFragmentManager().popBackStack();
                }
            }
        });
        ((Main2Activity)getActivity()).hideFab();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nueva_averia, container, false);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
