package com.example.power.fixthefault;

import android.content.Context;
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

import Logic.AdapterUsers;
import Logic.Averia;
import Logic.Usuario;
import Persistence.Persistencia;



public class UsersFragment extends Fragment implements AdapterUsers.OnItemClickListener {

    Persistencia persistencia;
    Button btnNueva;
    RecyclerView rv;
    List<Usuario> usuarios;
    List<String> usuariosKeys;
    AdapterUsers adapter;
    DatabaseReference myRef;
    // TODO: Rename and change types of parameters
    FragmentManager fragmentManager;

    public UsersFragment() {
            // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Usuarios");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("usuarios");
        persistencia = Persistencia.getInstance();

        btnNueva = (Button)getView().findViewById(R.id.btn_nueva_users);
        rv = (RecyclerView) getView().findViewById(R.id.reciclerUsers);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        usuarios = new ArrayList<>();
        usuariosKeys = new ArrayList<>();

        adapter = new AdapterUsers(usuarios);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(UsersFragment.this);

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
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnNueva.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {

            fragmentManager=getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contenedor,new Usuario_Registrar_Fragment()).addToBackStack("usersfragment").commit();
        }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false);

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

   @Override
    public void onItemClick(int position) {

        Usuario usuario=usuarios.get(position);
        String key=usuariosKeys.get(position);
        persistencia.setUsuario(usuario);
        persistencia.setKeyUsuario(key);
        Modifica_Borra_usuario_Fragment fragment= new Modifica_Borra_usuario_Fragment();
        fragmentManager=getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor,fragment).addToBackStack("usersfragment").commit();

    }
}
