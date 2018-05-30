package com.example.power.fixthefault;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Logic.AdapterAverias;
import Logic.Averia;
import Logic.Controlador;
import Logic.DialogPrioridad;


public class Averias_Principal_Fragment extends Fragment implements AdapterAverias.OnItemClickListener {
    Controlador controlador;
    RecyclerView rv;
    List<Averia> averias;
    List<String> averiasKeys;
    AdapterAverias adapter;
    DatabaseReference myRef;
    Dialog myDialog;
    FragmentManager fragmentManager;


    public Averias_Principal_Fragment() { }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("averias");
        controlador = Controlador.getInstance();
        rv = (RecyclerView) getView().findViewById(R.id.pruebarecicler);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        averias = new ArrayList<>();
        averiasKeys = new ArrayList<>();
        adapter = new AdapterAverias(averias);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(Averias_Principal_Fragment.this);
        addListenerFirebase();

       setTitle();
        ((Principal_Activity)getActivity()).showFab();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onBtnClick(int position) {
        if(controlador.getRolUsuario().equals("Admin")){
        controlador.setAveriaSeleccionada(averias.get(position));
        controlador.setKeyAveria(averiasKeys.get(position));
        myDialog = new Dialog(getActivity());
        ShowPopup();
        //DialogPrioridad myDialogo = new DialogPrioridad(myDialog);
        //myDialogo.muestraDialog();

        }
    }

    @Override
    public void onItemClick(int position) {
        Averia averia = averias.get(position);
        String key = averiasKeys.get(position);
        controlador.setAveriaSeleccionada(averia);
        controlador.setKeyAveria(key);
        Averias_Modifica_Borra_Fragment fragment = new Averias_Modifica_Borra_Fragment();
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor,fragment).addToBackStack("blankfragment").commit();
    }

    public void addListenerFirebase(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                averias.removeAll(averias);
                averiasKeys.removeAll(averiasKeys);
                String estadoAveria;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Averia averia = snapshot.getValue(Averia.class);
                    String key = snapshot.getKey();
                    estadoAveria=averia.getEstado();
                    if((!estadoAveria.equals("Terminada") || !estadoAveria.equals("En ejecución"))&& (averia.getPrioridad()!=5)){
                    averias.add(averia);
                    averiasKeys.add(key);}}
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });}

    public void setTitle(){ getActivity().setTitle("Averias");}

    public void ShowPopup() {

        Button urgente,moderada,media,leve,baja;
        myDialog.setContentView(R.layout.priority_popup);
        urgente = (Button) myDialog.findViewById(R.id.btn_urgente);
        moderada = (Button) myDialog.findViewById(R.id.btn_moderada);
        media = (Button) myDialog.findViewById(R.id.btn_media);
        leve = (Button) myDialog.findViewById(R.id.btn_leve);
        baja = (Button) myDialog.findViewById(R.id.btn_baja);
        urgente.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    Averia averia=controlador.getAveriaSeleccionada();
                    averia.setPrioridad(4);
                    controlador.guardaAveria(averia);
                    myDialog.dismiss();
                    }
                });
        moderada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Averia averia=controlador.getAveriaSeleccionada();
                averia.setPrioridad(3);
                controlador.guardaAveria(averia);
                myDialog.dismiss();
            }
        });
        media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Averia averia=controlador.getAveriaSeleccionada();
                averia.setPrioridad(2);
                controlador.guardaAveria(averia);
                myDialog.dismiss();
            }
        });
        leve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Averia averia=controlador.getAveriaSeleccionada();
                averia.setPrioridad(1);
                controlador.guardaAveria(averia);
                myDialog.dismiss();
            }
        });
        baja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Averia averia=controlador.getAveriaSeleccionada();
                averia.setPrioridad(0);
                controlador.guardaAveria(averia);
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}


