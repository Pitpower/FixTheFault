package Logic;

import android.content.Intent;
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

import com.example.power.fixthefault.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.AveriasviewHolder>{
    private OnItemClickListener mlistener;
    List<Averia> averias;

    public Adapter(List<Averia> averias) {

        this.averias = averias;
    }

    public interface OnItemClickListener{
            void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }

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
        holder.btn.setBackgroundColor(0xFF00FF00);
    }


    @Override
    public int getItemCount() {
        return averias.size();
    }

    public  class AveriasviewHolder extends RecyclerView.ViewHolder{
        TextView textViewLugar,textViewDescripcion;
        Button btn;

        public AveriasviewHolder(final View itemView) {
            super(itemView);
            textViewLugar = itemView.findViewById(R.id.textview_Lugar1);
            textViewDescripcion = itemView.findViewById(R.id.textview_Descripcion1);
            btn = itemView.findViewById(R.id.button3);

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
