package tiendaWallRose;

import java.util.ArrayList;

public class Cliente {

    private int id;
    private String nombre;
    private String email;

    private ArrayList<OrdenDeCompra> ordenes;

    public Cliente(int id, String nombre, String email) {

        this.id = id;
        this.nombre = nombre;
        this.email = email;

        ordenes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<OrdenDeCompra> getOrdenes() {
        return ordenes;
    }

    public void agregarOrden(OrdenDeCompra orden) {
        ordenes.add(orden);
    }

    public double getTotalPendiente() {

        double total = 0;

        for (OrdenDeCompra orden : ordenes) {

            if (orden.getEstado() == EstadoOrden.PENDIENTE) {
                total += orden.calcularTotal();
            }
        }

        return total;
    }

    public boolean validarEmail() {

        if (email.contains("@")) {
            return true;
        }

        return false;
    }
}
