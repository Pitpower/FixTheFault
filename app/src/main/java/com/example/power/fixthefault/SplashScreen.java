package com.example.power.fixthefault;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Logic.Controlador;
import Logic.Session;
import Logic.Usuario;

import static java.lang.Thread.sleep;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth mAuth;
    DatabaseReference myRef;
    Controlador controlador;
    ProgressBar progressbar;
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.loader);
        controlador = Controlador.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("usuarios");
        mAuth=FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                FirebaseUser user = mAuth.getCurrentUser();
                if(user!=null) {
                    Log.i("SESION INICIADA","Statechange- Usuario logeado:"+user.getEmail().toString());
                    // showProgress(false);
                    myRef.orderByChild("email").equalTo(user.getEmail()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Usuario usuario= (Usuario)dataSnapshot.getValue(Usuario.class);
                            controlador.setUsuarioSession(usuario);
                            Intent intentNueva=new Intent(getApplicationContext(), Principal_Activity.class);
                            startActivity(intentNueva);
                            finish();
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
                } else {

                    Intent intentNueva=new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intentNueva);
                    finish();
                }

        };
            },SPLASH_TIME_OUT);



}}
