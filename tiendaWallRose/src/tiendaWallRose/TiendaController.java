package tiendaWallRose;

import java.util.ArrayList;

public class TiendaController {

    private static TiendaController instancia;

    private ArrayList<Cliente> clientes;
    private ArrayList<Producto> productos;
    private ArrayList<OrdenDeCompra> ordenes;

    private int nextCodigoProducto;
    private int nextNumeroOrden;

    private TiendaController() {

        clientes = new ArrayList<>();
        productos = new ArrayList<>();
        ordenes = new ArrayList<>();

        nextCodigoProducto = 1;
        nextNumeroOrden = 1;
    }

    public static TiendaController getInstancia() {

        if (instancia == null) {
            instancia = new TiendaController();
        }

        return instancia;
    }

    // CLIENTES

    public ArrayList<Cliente> listarClientes() {
        return clientes;
    }

    public Cliente obtenerCliente(int id) {

        for (Cliente c : clientes) {

            if (c.getId() == id) {
                return c;
            }
        }

        return null;
    }

    public boolean crearCliente(int id,
                                String nombre,
                                String email) {

        if (obtenerCliente(id) != null) {
            return false;
        }

        Cliente cliente = new Cliente(id, nombre, email);

        if (!cliente.validarEmail()) {
            return false;
        }

        clientes.add(cliente);

        return true;
    }

    public boolean actualizarCliente(int id,
                                     String nombre,
                                     String email) {

        Cliente cliente = obtenerCliente(id);

        if (cliente == null) {
            return false;
        }

        cliente.setNombre(nombre);
        cliente.setEmail(email);

        return true;
    }

    public boolean eliminarCliente(int id) {

        Cliente cliente = obtenerCliente(id);

        if (cliente == null) {
            return false;
        }

        for (int i = 0; i < ordenes.size(); i++) {

            if (ordenes.get(i).getCliente().getId() == id) {
                ordenes.remove(i);
                i--;
            }
        }

        clientes.remove(cliente);

        return true;
    }

    // PRODUCTOS

    public ArrayList<Producto> listarProductos() {
        return productos;
    }

    public Producto obtenerProducto(int codigo) {

        for (Producto p : productos) {

            if (p.getCodigo() == codigo) {
                return p;
            }
        }

        return null;
    }

    public Producto crearProducto(String nombre,
                                  double existencias,
                                  Unidad unidad,
                                  double precio) {

        Producto producto = new Producto(
                nextCodigoProducto,
                nombre,
                existencias,
                unidad,
                precio
        );

        nextCodigoProducto++;

        productos.add(producto);

        return producto;
    }

    public boolean actualizarProducto(int codigo,
                                      String nombre,
                                      double existencias,
                                      Unidad unidad,
                                      double precio) {

        Producto producto = obtenerProducto(codigo);

        if (producto == null) {
            return false;
        }

        producto.actualizar(
                nombre,
                existencias,
                unidad,
                precio
        );

        return true;
    }

    public boolean eliminarProducto(int codigo) {

        Producto producto = obtenerProducto(codigo);

        if (producto == null) {
            return false;
        }

        for (OrdenDeCompra orden : ordenes) {

            for (DetalleOrden detalle : orden.getDetalles()) {

                if (detalle.getProducto().getCodigo() == codigo) {
                    return false;
                }
            }
        }

        productos.remove(producto);

        return true;
    }

    // ORDENES

    public ArrayList<OrdenDeCompra> listarOrdenes() {
        return ordenes;
    }

    public OrdenDeCompra obtenerOrden(int numero) {

        for (OrdenDeCompra orden : ordenes) {

            if (orden.getNumero() == numero) {
                return orden;
            }
        }

        return null;
    }

    public OrdenDeCompra crearOrden(int idCliente) {

        Cliente cliente = obtenerCliente(idCliente);

        if (cliente == null) {
            return null;
        }

        OrdenDeCompra orden = new OrdenDeCompra(
                nextNumeroOrden,
                cliente
        );

        nextNumeroOrden++;

        ordenes.add(orden);

        return orden;
    }

    public boolean agregarLinea(int numeroOrden,
                                int codigoProducto,
                                double cantidad) {

        OrdenDeCompra orden = obtenerOrden(numeroOrden);
        Producto producto = obtenerProducto(codigoProducto);

        if (orden == null || producto == null) {
            return false;
        }

        if (!producto.hayStock(cantidad)) {
            return false;
        }

        DetalleOrden detalle = new DetalleOrden(
                orden.getDetalles().size() + 1,
                producto,
                cantidad
        );

        orden.agregarDetalle(detalle);

        return true;
    }

    public boolean actualizarLinea(int numeroOrden,
                                   int numeroLinea,
                                   double cantidad) {

        OrdenDeCompra orden = obtenerOrden(numeroOrden);

        if (orden == null) {
            return false;
        }

        for (DetalleOrden detalle : orden.getDetalles()) {

            if (detalle.getNumeroLinea() == numeroLinea) {

                if (!detalle.getProducto().hayStock(cantidad)) {
                    return false;
                }

                detalle.setCantidad(cantidad);

                return true;
            }
        }

        return false;
    }

    public boolean eliminarLinea(int numeroOrden,
                                 int numeroLinea) {

        OrdenDeCompra orden = obtenerOrden(numeroOrden);

        if (orden == null) {
            return false;
        }

        orden.eliminarDetalle(numeroLinea);

        return true;
    }

    public boolean ponerPendiente(int numeroOrden) {

        OrdenDeCompra orden = obtenerOrden(numeroOrden);

        if (orden == null) {
            return false;
        }

        for (DetalleOrden detalle : orden.getDetalles()) {

            Producto producto = detalle.getProducto();

            if (!producto.hayStock(detalle.getCantidad())) {
                return false;
            }
        }

        for (DetalleOrden detalle : orden.getDetalles()) {

            Producto producto = detalle.getProducto();

            producto.reducirStock(detalle.getCantidad());
        }

        orden.setEstado(EstadoOrden.PENDIENTE);

        return true;
    }

    public boolean terminarOrden(int numeroOrden) {

        OrdenDeCompra orden = obtenerOrden(numeroOrden);

        if (orden == null) {
            return false;
        }

        orden.setEstado(EstadoOrden.TERMINADA);

        return true;
    }

    public double obtenerTotalPendiente() {

        double total = 0;

        for (OrdenDeCompra orden : ordenes) {

            if (orden.getEstado() == EstadoOrden.PENDIENTE) {
                total += orden.calcularTotal();
            }
        }

        return total;
    }
}
