package Persistence;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Logic.Averia;
import Logic.Usuario;

public class Persistencia {

    FirebaseDatabase database;
    final DatabaseReference myRefaverias;
    final DatabaseReference myRefusuarios;
    private Averia averia;
    private Averia averiaToModificar;
    private Usuario usuario;
    private Usuario usuarioModificado;
    private String keyUsuario;
    private String keyAveria;
    public boolean passwordActualizado;
    FirebaseAuth mAuth;
    //mAuth2 es para hacer gestiones de usuarios sin logear cada vez
    FirebaseAuth mAuth2;


    private static final Persistencia crud = new Persistencia();

    public static Persistencia getInstance() {
        return crud;
    }

    private Persistencia() {
        database = FirebaseDatabase.getInstance();
        myRefaverias = database.getReference("averias");
        myRefusuarios = database.getReference("usuarios");
        averiaToModificar=null;
        mAuth=FirebaseAuth.getInstance();
    }

    public void creaAuthparaUsers(Context context) {

        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setDatabaseUrl("[https://fixthefault.firebaseio.com]")
                .setApiKey("AIzaSyCdugYUPuezeqAp8LKhsHiaQ8cymGE4Q9k")
                .setApplicationId("1:851280112156:android:c578a84ba269169b").build();

        try { FirebaseApp myApp = FirebaseApp.initializeApp(context, firebaseOptions, "AnyAppName");
            mAuth2 = FirebaseAuth.getInstance(myApp);
        } catch (IllegalStateException e){
            mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"));
        }
    }

    public Averia obtenerAveria(String key){
        myRefaverias.orderByChild("key").equalTo(key).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                averia= dataSnapshot.getValue(Averia.class);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return averia;
    }
    public void guardaNuevaAveria(Averia averia){
        myRefaverias.push().setValue(averia);
    }

    public void guardaAveriaModificada( Averia averia, String key){
        myRefaverias.child(key).setValue(averia);
    }

    public void eliminaAveria(String key){
           myRefaverias.child(key).setValue(null);
    }

    public void registrarUsuario(final Usuario user){

        mAuth2.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           myRefusuarios.push().setValue(user);
                       }
                       else{

                       }
                   }
               });
    }

    public void cambiaPassword(Usuario usuario, final String nuevo_password){
        mAuth2.signInWithEmailAndPassword(usuario.getEmail(),usuario.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                   FirebaseUser user = mAuth2.getCurrentUser();
                   user.updatePassword(nuevo_password);
                   }
               }
        });
    }

    public void guardaUsuario(String key, Usuario usuario, Usuario usuarioSeleccionado){
        boolean cambios = false;

        if(!usuario.getNombre().equals(usuarioSeleccionado.getNombre())){
            cambios = true;
        }
        if(!usuario.getPassword().equals(usuarioSeleccionado.getPassword())){
            cambiaPassword(usuarioSeleccionado, usuario.getPassword());
            cambios = true;
        }
        if(!usuario.getRol().equals(usuarioSeleccionado.getRol())){
            cambios = true;
        }
       if(cambios){
        myRefusuarios.child(key).setValue(usuario);
        }
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void eliminaUsuario(String key, Usuario usuario){
        final String userKey = key;
         mAuth2.signInWithEmailAndPassword(usuario.getEmail(),usuario.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                FirebaseUser user = mAuth2.getCurrentUser();
                user.delete();
                myRefusuarios.child(userKey).setValue(null);}
            }
         });
    }

    public String getKeyAveria() {
        return keyAveria;
    }

    public void setKeyAveria(String keyAveria) {
        this.keyAveria = keyAveria;
    }

    public Averia getAveriaToModificar() {
        return averiaToModificar;
    }

    public Averia getAveria() {
        return averia;
    }


}
