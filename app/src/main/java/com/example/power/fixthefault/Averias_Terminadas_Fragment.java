package com.example.power.fixthefault;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import Logic.Averia;

public class Averias_Terminadas_Fragment extends Averias_Fragment {

    public boolean getCondicionDeEstado(String estadoAveria, int prioridad){return (estadoAveria.equals("Terminada"));}
    public void setTitle(){getActivity().setTitle("Terminadas");}
}
