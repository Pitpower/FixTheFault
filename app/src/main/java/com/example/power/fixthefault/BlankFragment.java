package com.example.power.fixthefault;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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

import Logic.Adapter;
import Logic.Averia;
import Persistence.Persistencia;


public class BlankFragment extends Fragment implements Adapter.OnItemClickListener {
    Persistencia persistencia;
    Button btnNueva;
    RecyclerView rv;
    List<Averia> averias;
    List<String> averiasKeys;
    Adapter adapter;
    DatabaseReference myRef;
    // TODO: Rename and change types of parameters
    FragmentManager fragmentManager;

    public BlankFragment() {
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

        adapter = new Adapter(averias);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(BlankFragment.this);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                averias.removeAll(averias);
                averiasKeys.removeAll(averiasKeys);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Averia averia = snapshot.getValue(Averia.class);
                    String key = snapshot.getKey();
                    averias.add(averia);
                    averiasKeys.add(key);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contenedor,new NuevaAveria_Frament()).addToBackStack("blankfragment").commit();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onItemClick(int position) {

        Averia averia = averias.get(position);
        String key = averiasKeys.get(position);
        Bundle args = new Bundle();
        args.putString("Lugar",averia.getUbicacion());
        args.putString("Descripcion",averia.getDescripcion());
        args.putString("Key",key);
        persistencia.setAveriaToModificar(averia);
        Modifica_Borra_averia_Fragment fragment = new Modifica_Borra_averia_Fragment();
        fragment.setArguments(args);
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor,fragment).addToBackStack("blankfragment").commit();
       // Intent averiasExtended = new Intent(getApplicationContext(), Averias_extended_activity.class);
       // averiasExtended.putExtra("averia",averia);
        //averiasExtended.putExtra("key",averiasKeys.get(position));
       // startActivity(averiasExtended);
        //Log.i("PRUEBAS",averiasKeys.get(position));
    }
}
