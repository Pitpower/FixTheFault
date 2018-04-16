package com.example.power.fixthefault;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Logic.Averia;
import Logic.Sesion;
import Logic.Session;

public class Averias_nueva_activity extends AppCompatActivity {
TextView textviewLugar,textviewDescripcion;
EditText editextLugar,editextDescripcion;
Button guardar;
  Session sesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_averias_nueva_);
   sesion=Session.getInstance();
        Log.i("Usuario",sesion.getUser().getNombre());
        textviewLugar=(TextView)findViewById(R.id.textview_nueva_lugar);
   textviewDescripcion=(TextView)findViewById(R.id.textview_nueva_descripcion);
   guardar=(Button)findViewById(R.id.btn_nueva_guardar);
   editextLugar=(EditText)findViewById(R.id.editext_nueva_lugar);
   editextDescripcion=(EditText)findViewById(R.id.editext_nueva_descripcion);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("averias");

guardar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Averia averia=new Averia(editextLugar.getText().toString(),editextDescripcion.getText().toString(),sesion.getUser());
        myRef.push().setValue(averia);
        Log.i("Usuario",sesion.getUser().getNombre());
        finish();
    }
});

    }
}
