package Logic;

import java.io.Serializable;
import java.util.Date;

public class Averia implements Serializable {
    public String ubicacion;
    public String descripcion;
    private Usuario user;
    private Usuario tecnico;
    private int prioridad;
    private Date fechaCreacion;
    private String estado;



    public Averia (String lugar,String descripcion,Usuario user) {
        this.ubicacion=lugar;
        this.descripcion=descripcion;
        this.user=user;
        fechaCreacion=new Date();
        prioridad=5;
        estado="En cola";

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

    public Usuario getTecnico() {
        return tecnico;
    }

    public void setTecnico(Usuario tecnico) {
        this.tecnico = tecnico;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
