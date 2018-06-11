package com.example.power.fixthefault;

import org.junit.Before;
import org.junit.Test;

import Logic.Averia;
import Logic.Controlador;
import Logic.Usuario;

import static org.junit.Assert.*;

public class CreaModificaTest {
    Usuario tecnico;
    Usuario empleado;
    Averia averia;

    @Before
    public void setUp() throws Exception {
        tecnico = new Usuario("Fernando_Técnico", "tecnico@tecnico.com", "Técnico", "tecnico");
        empleado = new Usuario("Alejandro_Empleado", "empleado@empleado.com", "Empleado", "empleado");
        averia = new Averia("Cocina", "Caldera rota", empleado);
    }
    @Test
    public void testNuevoUsuario() {
        empleado = new Usuario("Alejandro_Empleado", "empleado@empleado.com", "Empleado", "empleado");
        assertEquals("Alejandro_Empleado", empleado.getNombre());
        assertEquals("empleado@empleado.com", empleado.getEmail());
        assertEquals("empleado", empleado.getPassword());
        assertEquals("Empleado", empleado.getRol());
    }
    @Test
    public void testModificaUsuario() {
        empleado.setNombre("Rodrigo_Empleado");
        empleado.setEmail("rodrigo@empleado.com");
        empleado.setPassword("12345");
        empleado.setRol("Técnico");
        assertEquals("Rodrigo_Empleado", empleado.getNombre());
        assertEquals("rodrigo@empleado.com", empleado.getEmail());
        assertEquals("12345", empleado.getPassword());
        assertEquals("Técnico", empleado.getRol());

    }
    @Test
    public void testNuevaAveria() {
        averia = new Averia("Cocina", "Caldera rota", empleado);
        assertEquals("Caldera rota", averia.getDescripcion());
        assertEquals("Cocina", averia.getUbicacion());
        assertEquals("empleado@empleado.com", averia.getUser().getEmail());
        assertEquals(5, averia.getPrioridad());
        assertEquals("En cola", averia.getEstado());
        assertEquals(null, averia.getTecnico());

    }
    @Test
    public void testModificarAveria() {
        averia.setUbicacion("Cocina del restaurante");
        averia.setDescripcion("Caldera pierde agua");
        averia.setEstado("En ejecución");
        averia.setDescripcion("Caldera pierde agua");
        averia.setPrioridad(4);
        averia.setTecnico(tecnico);
        assertEquals("Caldera pierde agua", averia.getDescripcion());
        assertEquals("Cocina del restaurante", averia.getUbicacion());
        assertEquals("Fernando_Técnico", averia.getTecnico().getNombre());
        assertEquals(4, averia.getPrioridad());
        assertEquals("En ejecución", averia.getEstado());
    }

}