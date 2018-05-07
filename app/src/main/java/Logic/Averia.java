package Logic;

import java.io.Serializable;
import java.util.Date;

public class Averia implements Serializable {
    public String ubicacion;
    public String descripcion;
    private Usuario user;


    public Averia (String lugar,String descripcion,Usuario user) {
        this.ubicacion=lugar;this.descripcion=descripcion;this.user=user;
    }

    public Averia(){}

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
