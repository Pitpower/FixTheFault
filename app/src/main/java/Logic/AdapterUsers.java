package Logic;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.power.fixthefault.R;

import java.util.List;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.UsersViewHolder>{
    private OnItemClickListener mlistener;
    List<Usuario> usuarios;

    public AdapterUsers(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener=listener;
    }

    @Override
    public UsersViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recyclerusers,parent,false);
        UsersViewHolder holder = new UsersViewHolder(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(UsersViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.textViewNombre.setText(usuario.getNombre());
        holder.textViewEmail.setText(usuario.getEmail());
    }


    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public  class UsersViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNombre,textViewEmail;

        public UsersViewHolder(final View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textview_Nombre);
            textViewEmail = itemView.findViewById(R.id.textview_Email);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mlistener!=null){
                        int position=getAdapterPosition();
                        if(position!=-1){
                            mlistener.onItemClick(position);
                        }
                    }
                    // Averia aver=averias.get(position);

                    // Log.i("Myapp",aver.getUbicacion());
                }
            });
        }
    }
}