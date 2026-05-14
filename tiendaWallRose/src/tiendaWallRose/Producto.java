package tiendaWallRose;

public class Producto {

    private int codigo;
    private String nombre;
    private double existencias;
    private Unidad unidad;
    private double precio;

    public Producto(int codigo,
                    String nombre,
                    double existencias,
                    Unidad unidad,
                    double precio) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.existencias = existencias;
        this.unidad = unidad;
        this.precio = precio;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getExistencias() {
        return existencias;
    }

    public void setExistencias(double existencias) {
        this.existencias = existencias;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean hayStock(double cantidad) {

        if (existencias >= cantidad) {
            return true;
        }

        return false;
    }

    public void reducirStock(double cantidad) {
        existencias = existencias - cantidad;
    }

    public void actualizar(String nombre,
                            double existencias,
                            Unidad unidad,
                            double precio) {

        this.nombre = nombre;
        this.existencias = existencias;
        this.unidad = unidad;
        this.precio = precio;
    }
}