package tiendaWallRose;

public class Main {

    public static void main(String[] args) {

        TiendaController tc =
                TiendaController.getInstancia();

        tc.crearCliente(
                1,
                "Victoria",
                "Victoriamr@gmail.com"
        );

        tc.crearProducto(
                "Frijoles",
                100,
                Unidad.KG,
                1500
        );

        OrdenDeCompra orden =
                tc.crearOrden(1);

        tc.agregarLinea(
                orden.getNumero(),
                1,
                2
        );

        System.out.println(
                "Total: " + orden.calcularTotal()
        );
    }
}