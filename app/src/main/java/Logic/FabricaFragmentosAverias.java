package Logic;

import com.example.power.fixthefault.Averias_Fragment;
import com.example.power.fixthefault.Averias_NULL_Fragment;
import com.example.power.fixthefault.Averias_Principal_Fragment;
import com.example.power.fixthefault.Averias_SinPrioridad_Fragment;
import com.example.power.fixthefault.Averias_Terminadas_Fragment;
import com.example.power.fixthefault.Averias_enCurso_Fragment;

public class FabricaFragmentosAverias {

    public FabricaFragmentosAverias(){}

    public Averias_Fragment createFragment(String tipo){
        if(tipo.equals("principal"))
            return new Averias_Principal_Fragment();
            else if (tipo.equals("sinPrioridad"))
                return new Averias_SinPrioridad_Fragment();
                else if (tipo.equals("enCurso"))
                    return new Averias_enCurso_Fragment();
                    else if (tipo.equals("terminadas"))
                        return new Averias_Terminadas_Fragment();
         else return new Averias_NULL_Fragment();

    }

}
