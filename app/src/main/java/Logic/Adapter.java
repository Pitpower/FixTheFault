package Logic;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.power.fixthefault.Main2Activity;
import com.example.power.fixthefault.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.AveriasviewHolder>{
    private OnItemClickListener mlistener;
    List<Averia> averias;

    public Adapter(List<Averia> averias) {

        this.averias = averias;
    }

    public interface OnItemClickListener{
            void onItemClick(int position);
            void onBtnClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }
    public void jajaja(){}

    @Override
    public AveriasviewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_prioridad,parent,false);
        AveriasviewHolder holder=new AveriasviewHolder(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(AveriasviewHolder holder, int position) {

        Averia averia = averias.get(position);

        holder.textViewLugar.setText(averia.getUbicacion());
        holder.textViewDescripcion.setText(averia.getDescripcion());
        int prioridad=averia.getPrioridad();
        if(prioridad==0)
            holder.btn.setBackgroundColor(0xFF33B5E5);
            else if(prioridad==1)
                 holder.btn.setBackgroundColor(0xFF99CC00);
                else if(prioridad==2)
                     holder.btn.setBackgroundColor(Color.parseColor("#FFFF4D"));
                     else if(prioridad==3)
                         holder.btn.setBackgroundColor(0xFFFFBB33);
                        else if(prioridad==4)
                            holder.btn.setBackgroundColor(0xFFFF4444);
                            else
                                holder.btn.setBackgroundColor(Color.parseColor("#cccccc"));


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());


        String fecha = dateFormat.format(averia.getFechaCreacion());
        holder.textViewFecha.setText(fecha);


    }


    @Override
    public int getItemCount() {
        return averias.size();
    }

    public  class AveriasviewHolder extends RecyclerView.ViewHolder{
        TextView textViewLugar,textViewDescripcion,textViewFecha;
        Button btn;

        public AveriasviewHolder(final View itemView) {
            super(itemView);
            textViewLugar = itemView.findViewById(R.id.textview_Lugar1);
            textViewDescripcion = itemView.findViewById(R.id.textview_Descripcion1);
            textViewFecha = itemView.findViewById(R.id.textViewFecha);
            btn = itemView.findViewById(R.id.button3);
            btn.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    if(mlistener!=null){
                        int position = getAdapterPosition();
                        if (position != -1){
                            mlistener.onBtnClick(position);
                        }
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    if(mlistener!=null){
                        int position = getAdapterPosition();
                        if (position != -1){
                            mlistener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
