package tiendaWallRose;

public class DetalleOrden {

    private int numeroLinea;
    private Producto producto;
    private double cantidad;

    public DetalleOrden(int numeroLinea,
                        Producto producto,
                        double cantidad) {

        this.numeroLinea = numeroLinea;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public int getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(int numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double calcularCosto() {

        return cantidad * producto.getPrecio();
    }
}