package com.example.power.fixthefault;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Logic.Averia;

public class Averias_extended_activity extends AppCompatActivity {
String key;
Averia averia;
TextView etiquetaDescripcion,etiquetaLugar;
EditText editableLugar,editableDescripcion;
Button btnguardar;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_averias_extended);
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("averias");
        etiquetaLugar=(TextView)findViewById(R.id.textview_lugar);
        etiquetaDescripcion=(TextView)findViewById(R.id.textview_descripcion);
        editableLugar=(EditText)findViewById(R.id.editTextLugar);
        editableDescripcion=(EditText)findViewById(R.id.editTextDescripcion);
        btnguardar=(Button)findViewById(R.id.btn_guardar);

        etiquetaLugar.setText("Lugar");
        etiquetaDescripcion.setText("Descripcion");
        Intent intent =getIntent();
        averia=(Averia)intent.getSerializableExtra("averia");
        key=intent.getStringExtra("key");

        editableLugar.setText(averia.getUbicacion());
        editableDescripcion.setText(averia.getDescripcion());


        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(key).setValue(getAveria());
                finish();
            }
        });

        Log.i("KEY",key);

    }

    public Averia getAveria(){
    averia.setUbicacion(editableLugar.getText().toString());
    averia.setDescripcion(editableDescripcion.getText().toString());
    return averia;
    }

}
