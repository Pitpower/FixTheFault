package com.example.power.fixthefault;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import Logic.Averia;

public class Averias_enCurso_Fragment extends Averias_Principal_Fragment {
    @Override
    public void setTitle(){getActivity().setTitle("En curso");}
@Override
    public void addListenerFirebase(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                averias.removeAll(averias);
                averiasKeys.removeAll(averiasKeys);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Averia averia = snapshot.getValue(Averia.class);
                    String key = snapshot.getKey();
                    if(averia.getEstado().equals("En ejecución")){
                    averias.add(averia);
                    averiasKeys.add(key);}}


                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });}
}
