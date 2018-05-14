package com.example.power.fixthefault;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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

import Logic.Session;
import Logic.Usuario;

import static java.lang.Thread.sleep;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreen extends AppCompatActivity {
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth mAuth;
    DatabaseReference myRef;
    Session sesion;
    ProgressBar progressbar;
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.loader);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("usuarios");
        sesion=Session.getInstance();

        mAuth=FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
        mAuthListener=new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user!=null) {
                    Log.i("SESION INICIADA","Statechange- Usuario logeado:"+user.getEmail().toString());
                    // showProgress(false);
                    myRef.orderByChild("email").equalTo(user.getEmail()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Usuario usuario= (Usuario)dataSnapshot.getValue(Usuario.class);
                            sesion.setUser(usuario);
                            Intent intentNueva=new Intent(getApplicationContext(), Main2Activity.class);
                            startActivity(intentNueva);
                            // Log.i("Objeto","usuario="+sesion.getUser().getNombre());
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
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
            }},SPLASH_TIME_OUT);









}}
