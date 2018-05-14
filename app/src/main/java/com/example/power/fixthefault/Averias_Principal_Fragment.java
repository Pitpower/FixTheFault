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
import Persistence.Persistencia;


public class Averias_Principal_Fragment extends Fragment implements AdapterAverias.OnItemClickListener {
    Persistencia persistencia;
    Button btnNueva;
    RecyclerView rv;
    List<Averia> averias;
    List<String> averiasKeys;
    AdapterAverias adapter;
    DatabaseReference myRef;
    Dialog myDialog;
    // TODO: Rename and change types of parameters
    FragmentManager fragmentManager;

    public Averias_Principal_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("averias");
        persistencia = Persistencia.getInstance();

        btnNueva = (Button)getView().findViewById(R.id.btn_nueva);
        rv = (RecyclerView) getView().findViewById(R.id.pruebarecicler);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        averias = new ArrayList<>();
        averiasKeys = new ArrayList<>();

        adapter = new AdapterAverias(averias);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(Averias_Principal_Fragment.this);
        addListenerFirebase();

        btnNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contenedor,new Averia_Nueva_Fragment()).addToBackStack("blankfragment").commit();
            }
        });
    getActivity().setTitle("Averias");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onBtnClick(int position) {
        Averia averia = averias.get(position);
        String key = averiasKeys.get(position);
        persistencia.setAveriaToModificar(averia);
        persistencia.setKeyAveria(key);

        myDialog = new Dialog(getActivity());
        ShowPopup(getView());

    }



    @Override
    public void onItemClick(int position) {

        Averia averia = averias.get(position);
        String key = averiasKeys.get(position);
        persistencia.setAveriaToModificar(averia);
        persistencia.setKeyAveria(key);
        Modifica_Borra_averia_Fragment fragment = new Modifica_Borra_averia_Fragment();
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor,fragment).addToBackStack("blankfragment").commit();

    }

    public void addListenerFirebase(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                averias.removeAll(averias);
                averiasKeys.removeAll(averiasKeys);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Averia averia = snapshot.getValue(Averia.class);
                    String key = snapshot.getKey();

                    averias.add(averia);
                    averiasKeys.add(key);}


                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });}

    public void ShowPopup(View v) {

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
                    Averia averia=persistencia.getAveriaToModificar();
                    averia.setPrioridad(4);
                    persistencia.guardaAveriaModificada(averia);
                    myDialog.dismiss();
                    }
                });
        moderada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Averia averia=persistencia.getAveriaToModificar();
                averia.setPrioridad(3);
                persistencia.guardaAveriaModificada(averia);
                myDialog.dismiss();
            }
        });
        media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Averia averia=persistencia.getAveriaToModificar();
                averia.setPrioridad(2);
                persistencia.guardaAveriaModificada(averia);
                myDialog.dismiss();
            }
        });
        leve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Averia averia=persistencia.getAveriaToModificar();
                averia.setPrioridad(1);
                persistencia.guardaAveriaModificada(averia);
                myDialog.dismiss();
            }
        });
        baja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Averia averia=persistencia.getAveriaToModificar();
                averia.setPrioridad(0);
                persistencia.guardaAveriaModificada(averia);
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}


