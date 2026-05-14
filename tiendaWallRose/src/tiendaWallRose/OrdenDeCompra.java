package tiendaWallRose;

import java.util.ArrayList;
import java.util.Date;

public class OrdenDeCompra {

    private int numero;
    private Date fecha;
    private EstadoOrden estado;

    private Cliente cliente;

    private ArrayList<DetalleOrden> detalles;

    public OrdenDeCompra(int numero, Cliente cliente) {

        this.numero = numero;
        this.cliente = cliente;

        fecha = new Date();

        estado = EstadoOrden.INICIADA;

        detalles = new ArrayList<>();

        cliente.agregarOrden(this);
    }

    public int getNumero() {
        return numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ArrayList<DetalleOrden> getDetalles() {
        return detalles;
    }

    public void agregarDetalle(DetalleOrden detalle) {

        if (estado != EstadoOrden.TERMINADA) {
            detalles.add(detalle);
        }
    }

    public void eliminarDetalle(int numeroLinea) {

        for (int i = 0; i < detalles.size(); i++) {

            if (detalles.get(i).getNumeroLinea() == numeroLinea) {
                detalles.remove(i);
                break;
            }
        }
    }

    public double calcularSubtotal() {

        double subtotal = 0;

        for (DetalleOrden detalle : detalles) {
            subtotal += detalle.calcularCosto();
        }

        return subtotal;
    }

    public double calcularImpuesto() {

        return calcularSubtotal() * 0.13;
    }

    public double calcularTotal() {

        return calcularSubtotal() + calcularImpuesto();
    }

    public boolean puedeEditar() {

        if (estado == EstadoOrden.TERMINADA) {
            return false;
        }

        return true;
    }
}