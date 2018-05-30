package Logic;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;

import com.example.power.fixthefault.R;

import java.util.ResourceBundle;

public class DialogPrioridad {
    Dialog myDialogo;
    Button urgente,moderada,media,leve,baja;
    View view;
    Controlador controlador;

    public DialogPrioridad(Dialog myDialog){
        myDialogo = myDialog;
        urgente = (Button) myDialogo.findViewById(R.id.btn_urgente);
        moderada = (Button) myDialogo.findViewById(R.id.btn_moderada);
        media = (Button) myDialogo.findViewById(R.id.btn_media);
        leve = (Button) myDialogo.findViewById(R.id.btn_leve);
        baja = (Button) myDialogo.findViewById(R.id.btn_baja);
        controlador = Controlador.getInstance();
        iniciaListeners();
    }

    public void muestraDialog(){
        myDialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialogo.show();
    }

    private void iniciaListeners(){
        myDialogo.setContentView(R.layout.priority_popup);
        urgente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Averia averia=controlador.getAveriaSeleccionada();
                averia.setPrioridad(4);
                controlador.guardaAveria(averia);
                myDialogo.dismiss();
            }
        });
        moderada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Averia averia=controlador.getAveriaSeleccionada();
                averia.setPrioridad(3);
                controlador.guardaAveria(averia);
                myDialogo.dismiss();
            }
        });
        media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Averia averia=controlador.getAveriaSeleccionada();
                averia.setPrioridad(2);
                controlador.guardaAveria(averia);
                myDialogo.dismiss();
            }
        });
        leve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Averia averia=controlador.getAveriaSeleccionada();
                averia.setPrioridad(1);
                controlador.guardaAveria(averia);
                myDialogo.dismiss();
            }
        });
        baja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Averia averia=controlador.getAveriaSeleccionada();
                averia.setPrioridad(0);
                controlador.guardaAveria(averia);
                myDialogo.dismiss();
            }
        });

    }


    }