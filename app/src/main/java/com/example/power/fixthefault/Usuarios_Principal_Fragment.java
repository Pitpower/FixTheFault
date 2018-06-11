package com.example.power.fixthefault;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Logic.AdapterUsers;
import Logic.Controlador;
import Logic.FabricaAdaptadores;
import Logic.Usuario;


public class Usuarios_Principal_Fragment extends Fragment implements AdapterUsers.OnItemClickListener {

    RecyclerView rv;
    List<Usuario> usuarios;
    List<String> usuariosKeys;
    //AdapterUsers adapter;
    AdapterUsers adaptador;
    DatabaseReference myRef;
    FragmentManager fragmentManager;
    Controlador controlador;

    public Usuarios_Principal_Fragment() { }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Usuarios");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("usuarios");
        controlador = Controlador.getInstance();
        rv = (RecyclerView) getView().findViewById(R.id.reciclerUsers);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        usuarios = new ArrayList<>();
        usuariosKeys = new ArrayList<>();
        FabricaAdaptadores fabrica = new FabricaAdaptadores();
        adaptador = (AdapterUsers) fabrica.crearAdaptador(usuarios);
        rv.setAdapter(adaptador);
        adaptador.setOnItemClickListener(Usuarios_Principal_Fragment.this);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuarios.removeAll(usuarios);
                usuariosKeys.removeAll(usuariosKeys);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Usuario usuario = snapshot.getValue(Usuario.class);
                    String key = snapshot.getKey();
                    usuarios.add(usuario);
                    usuariosKeys.add(key);

                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ((Principal_Activity) getActivity()).showFab();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

   @Override
    public void onItemClick(int position) {

        Usuario usuario=usuarios.get(position);
        String key=usuariosKeys.get(position);
        controlador.setUsuarioSelecionado(usuario);
        controlador.setKeyUsuario(key);
        Usuarios_Modifica_Borra_Fragment fragment= new Usuarios_Modifica_Borra_Fragment();
        fragmentManager=getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor,fragment).addToBackStack("usersfragment").commit();

    }
}
