package com.example.power.fixthefault;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import Logic.Session;


public class NuevaAveria_Frament extends Fragment {
    TextView textviewLugar,textviewDescripcion;
    EditText editextLugar,editextDescripcion;
    Button guardar;
    Session sesion;

    private OnFragmentInteractionListener mListener;

    public NuevaAveria_Frament() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Nueva averia");
        sesion=Session.getInstance();
        Log.i("Usuario",sesion.getUser().getNombre());
        textviewLugar=(TextView)getView().findViewById(R.id.textview_fragment_nueva_lugar);
        textviewDescripcion=(TextView)getView().findViewById(R.id.textview_fragment_nueva_descripcion);
        guardar=(Button)getView().findViewById(R.id.btn_fragment_nueva_guardar);
        editextLugar=(EditText)getView().findViewById(R.id.editext_fragment_nueva_lugar);
        editextDescripcion=(EditText)getView().findViewById(R.id.editext_fragment_nueva_descripcion);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("averias");

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Averia averia=new Averia(editextLugar.getText().toString(),editextDescripcion.getText().toString(),sesion.getUser());
                myRef.push().setValue(averia);
                Log.i("Usuario",sesion.getUser().getNombre());

                getFragmentManager().popBackStack();
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nueva_averia, container, false);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
