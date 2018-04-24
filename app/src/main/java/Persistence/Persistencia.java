package Persistence;

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
    private Averia averia;
    private Averia averiaToModificar;
    private static final Persistencia crud = new Persistencia();

   public static Persistencia getInstance() {
        return crud;
    }

    private Persistencia() {
         database = FirebaseDatabase.getInstance();
        myRefaverias = database.getReference("averias");
        averiaToModificar=null;

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
public void setAveriaToModificar(Averia averia){averiaToModificar=averia;}

public void guardaAveriaModificada(String key, Averia averia){
        averia.setUser(averiaToModificar.getUser());
        myRefaverias.child(key).setValue(averia);

}
public void eliminaAveria(String key){
       myRefaverias.child(key).setValue(null);
}


}
