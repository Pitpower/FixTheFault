package Logic;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;

import junit.framework.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class FabricaAdaptadores {
    public FabricaAdaptadores(){}
    public RecyclerView.Adapter crearAdaptador(List lista)  {
        List<Usuario> listaUsers= new ArrayList();
        List<Averia> listaAverias = new ArrayList();
        try {
            listaUsers = lista;
            return new AdapterUsers(lista);
        } catch (Exception e){return new AdapterAverias(lista);}

    }
}
