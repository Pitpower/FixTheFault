package Logic;

import android.content.Context;

import com.example.power.fixthefault.Averias_Fragment;
import com.example.power.fixthefault.Averias_NULL_Fragment;
import com.example.power.fixthefault.Averias_Principal_Fragment;
import com.example.power.fixthefault.Averias_SinPrioridad_Fragment;
import com.example.power.fixthefault.Averias_Terminadas_Fragment;
import com.example.power.fixthefault.Averias_enCurso_Fragment;

import Persistence.Persistencia;

public class Controlador {
    private static final Controlador ourInstance = new Controlador();
    private Persistencia persistencia;
    private Session sesion;
    private Usuario usuarioSelecionado;
    private String keyUsuario;
    private Averia averiaSeleccionada;
    private String keyAveria;

    public static Controlador getInstance() {
        return ourInstance;
    }

    private Controlador() {
        sesion = Session.getInstance();
        persistencia = Persistencia.getInstance();
    }

    public Averias_Fragment creaFragmento(String tipo){
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
    public Usuario getUsuarioLogeado(){
        return sesion.getUser();
    }

    public void guardaNuevaAveria(Averia averia){
        persistencia.guardaNuevaAveria(averia);
    }

    public void guardaAveria(Averia averia){
        persistencia.guardaAveriaModificada(averia, keyAveria);
    }
    public void eliminaAveria(){
        persistencia.eliminaAveria(keyAveria);
    }

    public void guardaUsuario(Usuario usuario){
        persistencia.guardaUsuario(keyUsuario, usuario);
    }

    public void eliminarUsuario(){
        persistencia.eliminaUsuario(keyUsuario,usuarioSelecionado);
    }

    public void registrarUsuario(Usuario usuario){
        persistencia.registrarUsuario(usuario);

    }
    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

    public String getKeyUsuario() {
        return keyUsuario;
    }

    public void setKeyUsuario(String keyUsuario) {
        this.keyUsuario = keyUsuario;
    }

    public Averia getAveriaSeleccionada() {
        return averiaSeleccionada;
    }

    public void setAveriaSeleccionada(Averia averiaSeleccionada) {
        this.averiaSeleccionada = averiaSeleccionada;
    }

    public String getKeyAveria() {
        return keyAveria;
    }

    public void setKeyAveria(String keyAveria) {
        this.keyAveria = keyAveria;
    }

    public String getRolUsuario(){return sesion.getUser().getRol();}

    public void creaAuthparaUsers(Context context){
        persistencia.creaAuthparaUsers(context);
    };

    public void setUsuarioSession(Usuario usuario){sesion.setUser(usuario);}



}
