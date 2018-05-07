package com.example.power.fixthefault;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Logic.Adapter;
import Logic.Averia;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemClickListener {
    Button btnNueva;
    RecyclerView rv;
    List<Averia> averias;
    List<String> averiasKeys;
    Adapter adapter;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("averias");

       // Averia pruebaaveria =new Averia("Cocina","grifo roto");
         //myRef.push().setValue(pruebaaveria);
        btnNueva = (Button)findViewById(R.id.btn_nueva);
        rv = (RecyclerView) findViewById(R.id.pruebarecicler);
        rv.setLayoutManager(new LinearLayoutManager(this));
        averias = new ArrayList<>();
        averiasKeys = new ArrayList<>();

        adapter = new Adapter(averias);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(MainActivity.this);
        loadSwipe();

        btnNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNueva = new Intent(getApplicationContext(), Averias_nueva_activity.class);
                startActivity(intentNueva);
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                averias.removeAll(averias);
                averiasKeys.removeAll(averiasKeys);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Averia averia = snapshot.getValue(Averia.class);
                    String key = snapshot.getKey();
                    averias.add(averia);
                    averiasKeys.add(key);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Averia averia=averias.get(position);
        Intent averiasExtended = new Intent(getApplicationContext(), Averias_extended_activity.class);
       averiasExtended.putExtra("averia",averia);
        averiasExtended.putExtra("key",averiasKeys.get(position));
        startActivity(averiasExtended);
        //Log.i("PRUEBAS",averiasKeys.get(position));
    }

    public void loadSwipe(){

        ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                myRef.child(averiasKeys.get(viewHolder.getAdapterPosition())).setValue(null);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Paint color = new Paint();

                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemview = viewHolder.itemView;

                    if(dX>0){
                        color.setColor(Color.parseColor("#01A9DB"));
                        RectF fondo = new RectF((float) itemview.getRight(),(float)itemview.getTop(),dX,(float)itemview.getBottom());
                        c.drawRect(fondo,color);
                    } else {
                        color.setColor(Color.parseColor("#01DFA5"));
                        RectF fondo = new RectF((float) itemview.getRight(),(float)itemview.getTop(),itemview.getRight(),(float)itemview.getBottom());
                        c.drawRect(fondo,color);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rv);
        }

}
