package Logic;

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
}
