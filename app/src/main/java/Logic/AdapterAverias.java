package Logic;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.power.fixthefault.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AdapterAverias extends RecyclerView.Adapter<AdapterAverias.AveriasviewHolder>{
    private OnItemClickListener mlistener;
    List<Averia> averias;

    public AdapterAverias(List<Averia> averias) {

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
        // holder.btn.setBackgroundColor(getColor(averia.getPrioridad()));
        holder.btn.setText(setLetter(averia.getPrioridad()));
        holder.btn.setBackgroundResource(getColor(averia.getPrioridad()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String fecha = dateFormat.format(averia.getFechaCreacion());
        holder.textViewFecha.setText(fecha);
    }

    private int getColor(int prioridad){
        if(prioridad == 0)
            return R.drawable.buttonprioridadbaja;
        else if(prioridad==1)
            return R.drawable.buttonprioridadleve;
        else if(prioridad==2)
            return R.drawable.buttonprioridadmedia;
        else if(prioridad==3)
            return R.drawable.buttonprioridadmoderada;
        else if(prioridad==4)
            return R.drawable.buttonprioridadurgente;
         else
            return Color.parseColor("#cccccc");
    }

    private String setLetter(int prioridad){
        if(prioridad == 0)
            return "B";
        else if(prioridad==1)
            return "L";
        else if(prioridad==2)
            return "M";
        else if(prioridad==3)
            return "M";
        else if(prioridad==4)
            return "U";
        else
            return "";
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
