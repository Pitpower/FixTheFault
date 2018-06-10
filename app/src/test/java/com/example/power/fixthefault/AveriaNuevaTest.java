package com.example.power.fixthefault;

import org.junit.Before;
import org.junit.Test;

import Logic.Averia;
import Logic.Usuario;

import static org.junit.Assert.*;

public class AveriaNuevaTest {

    @Before
    public void setUp() throws Exception {


        /*Usuario userTecnico = new Usuario("Borja", "borja@journify.es", "tecnico", "gagaga");
        Averia averia2 = new Averia("Habitacion", "Puerta", userTecnico);

        Usuario userEmpleado = new Usuario("Borja", "borja@journify.es", "normal", "gagaga");
        Averia averia3 = new Averia("Sala de bombas", "Fuga de agua", userEmpleado);*/

    }

    @Test
    public void testNuevaAveria() {
        Usuario userAdmin = new Usuario("Borja", "borja@journify.es", "admin", "gagaga");
        Averia averia = new Averia("Cocina", "Caldera rota", userAdmin);
        assertEquals("Caldera rota", averia.getDescripcion());
        assertEquals("Cocina", averia.getUbicacion());
        assertEquals("borja@journify.es", averia.getUser().getEmail());
    }

    @Test
    public void testModificarAveria() {
        Usuario userEmpleado = new Usuario("Borja", "borja@journify.es", "normal", "gagaga");
        Averia averia3 = new Averia("Sala de bombas", "Fuga de agua", userEmpleado);
        averia3.setUbicacion("Habitacion 22");
        assertEquals("Habitacion 22", averia3.getUbicacion());
    }
}