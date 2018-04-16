package Logic;

public class Sesion {
    private static final Sesion sesion=null;
    private Usuario user;

    private Sesion(){}

    public static Sesion getInstance(){
        if(sesion==null){
            return new Sesion();
        }else{
            return sesion;
        }

    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
