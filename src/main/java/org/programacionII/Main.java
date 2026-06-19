package org.programacionII;

import org.programacionII.entities.*;
import org.programacionII.entities.enums.Estado;
import org.programacionII.entities.enums.FormaPago;
import org.programacionII.entities.enums.Rol;
import org.programacionII.exception.DatoInvalidoException;
import org.programacionII.services.CategoriaService;
import org.programacionII.services.PedidoService;
import org.programacionII.services.ProductoService;
import org.programacionII.services.UsuarioService;

public class Main {

    private static java.util.Scanner scanner = new java.util.Scanner(System.in);

    private static CategoriaService categoriaService = new CategoriaService();
    private static UsuarioService usuarioService = new UsuarioService();
    private static ProductoService productoService = new ProductoService(categoriaService);
    private static PedidoService pedidoService = new PedidoService(usuarioService, productoService);

    public static void main(String[] args) {
        cargarDatosIniciales();

        int opcion;

        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    menuCategorias();
                    break;
                case 2:
                    menuProductos();
                    break;
                case 3:
                    menuUsuarios();
                    break;
                case 4:
                    menuPedidos();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
                    break;
            }

        } while (opcion != 0);
    }

    private static void mostrarMenuPrincipal() {
        System.out.println();
        System.out.println("=== SISTEMA DE PEDIDOS FOOD STORE ===");
        System.out.println("1. Categorias");
        System.out.println("2. Productos");
        System.out.println("3. Usuarios");
        System.out.println("4. Pedidos");
        System.out.println("0. Salir");
    }

    private static void menuCategorias() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== CATEGORIAS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opcion: ");

            try {
                switch (opcion) {
                    case 1:
                        listarCategorias();
                        break;
                    case 2:
                        crearCategoria();
                        break;
                    case 3:
                        editarCategoria();
                        break;
                    case 4:
                        eliminarCategoria();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                        break;
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    private static void listarCategorias() {
        java.util.List<Categoria> categorias = categoriaService.listar();

        if (categorias.isEmpty()) {
            System.out.println("No hay categorias cargadas.");
            return;
        }

        System.out.println();
        System.out.println("Listado de categorias:");

        for (Categoria categoria : categorias) {
            System.out.println(categoria);
        }
    }

    private static void crearCategoria() {
        System.out.println();
        System.out.println("Crear categoria");

        String nombre = leerTexto("Nombre: ");
        String descripcion = leerTexto("Descripcion: ");

        Categoria categoria = categoriaService.crear(nombre, descripcion);

        System.out.println("Categoria creada correctamente. Id generado: " + categoria.getId());
    }

    private static void editarCategoria() {
        listarCategorias();

        Long id = leerLong("Ingrese el id de la categoria a editar: ");
        String nombre = leerTexto("Nuevo nombre: ");
        String descripcion = leerTexto("Nueva descripcion: ");

        Categoria categoria = categoriaService.editar(id, nombre, descripcion);

        System.out.println("Categoria actualizada correctamente: " + categoria);
    }

    private static void eliminarCategoria() {
        listarCategorias();

        Long id = leerLong("Ingrese el id de la categoria a eliminar: ");
        String confirma = leerTexto("Confirma eliminacion? S/N: ");

        if (confirma.equalsIgnoreCase("S")) {
            categoriaService.eliminar(id);
            System.out.println("Categoria eliminada correctamente.");
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    private static void menuProductos() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== PRODUCTOS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opcion: ");

            try {
                switch (opcion) {
                    case 1:
                        listarProductos();
                        break;
                    case 2:
                        crearProducto();
                        break;
                    case 3:
                        editarProducto();
                        break;
                    case 4:
                        eliminarProducto();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                        break;
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    private static void listarProductos() {
        java.util.List<Producto> productos = productoService.listar();

        if (productos.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }

        System.out.println();
        System.out.println("Listado de productos:");

        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

    private static void crearProducto() {
        System.out.println();
        System.out.println("Crear producto");

        if (categoriaService.listar().isEmpty()) {
            System.out.println("Primero debe cargar una categoria.");
            return;
        }

        listarCategorias();

        String nombre = leerTexto("Nombre: ");
        String descripcion = leerTexto("Descripcion: ");
        Double precio = leerDouble("Precio: ");
        Integer stock = leerEntero("Stock: ");
        String imagen = leerTexto("Imagen: ");
        Boolean disponible = leerBoolean("Disponible? S/N: ");
        Long categoriaId = leerLong("Id categoria: ");

        Producto producto = productoService.crear(nombre, descripcion, precio, stock, imagen, disponible, categoriaId);

        System.out.println("Producto creado correctamente. Id generado: " + producto.getId());
    }

    private static void editarProducto() {
        listarProductos();

        if (productoService.listar().isEmpty()) {
            return;
        }

        listarCategorias();

        Long id = leerLong("Ingrese el id del producto a editar: ");
        String nombre = leerTexto("Nuevo nombre: ");
        String descripcion = leerTexto("Nueva descripcion: ");
        Double precio = leerDouble("Nuevo precio: ");
        Integer stock = leerEntero("Nuevo stock: ");
        String imagen = leerTexto("Nueva imagen: ");
        Boolean disponible = leerBoolean("Disponible? S/N: ");
        Long categoriaId = leerLong("Nuevo id categoria: ");

        Producto producto = productoService.editar(id, nombre, descripcion, precio, stock, imagen, disponible, categoriaId);

        System.out.println("Producto actualizado correctamente: " + producto);
    }

    private static void eliminarProducto() {
        listarProductos();

        Long id = leerLong("Ingrese el id del producto a eliminar: ");
        String confirma = leerTexto("Confirma eliminacion? S/N: ");

        if (confirma.equalsIgnoreCase("S")) {
            productoService.eliminar(id);
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    private static void menuUsuarios() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== USUARIOS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opcion: ");

            try {
                switch (opcion) {
                    case 1:
                        listarUsuarios();
                        break;
                    case 2:
                        crearUsuario();
                        break;
                    case 3:
                        editarUsuario();
                        break;
                    case 4:
                        eliminarUsuario();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                        break;
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    private static void listarUsuarios() {
        java.util.List<Usuario> usuarios = usuarioService.listar();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
            return;
        }

        System.out.println();
        System.out.println("Listado de usuarios:");

        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    private static void crearUsuario() {
        System.out.println();
        System.out.println("Crear usuario");

        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String mail = leerTexto("Mail: ");
        String celular = leerTexto("Celular: ");
        String contrasenia = leerTexto("Contrasenia: ");
        Rol rol = seleccionarRol();

        Usuario usuario = usuarioService.crear(nombre, apellido, mail, celular, contrasenia, rol);

        System.out.println("Usuario creado correctamente. Id generado: " + usuario.getId());
    }

    private static void editarUsuario() {
        listarUsuarios();

        Long id = leerLong("Ingrese el id del usuario a editar: ");
        String nombre = leerTexto("Nuevo nombre: ");
        String apellido = leerTexto("Nuevo apellido: ");
        String mail = leerTexto("Nuevo mail: ");
        String celular = leerTexto("Nuevo celular: ");
        String contrasenia = leerTexto("Nueva contrasenia: ");
        Rol rol = seleccionarRol();

        Usuario usuario = usuarioService.editar(id, nombre, apellido, mail, celular, contrasenia, rol);

        System.out.println("Usuario actualizado correctamente: " + usuario);
    }

    private static void eliminarUsuario() {
        listarUsuarios();

        Long id = leerLong("Ingrese el id del usuario a eliminar: ");
        String confirma = leerTexto("Confirma eliminacion? S/N: ");

        if (confirma.equalsIgnoreCase("S")) {
            usuarioService.eliminar(id);
            System.out.println("Usuario eliminado correctamente.");
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    private static void menuPedidos() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== PEDIDOS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear pedido con detalles");
            System.out.println("3. Actualizar estado y forma de pago");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opcion: ");

            try {
                switch (opcion) {
                    case 1:
                        listarPedidos();
                        break;
                    case 2:
                        crearPedido();
                        break;
                    case 3:
                        actualizarPedido();
                        break;
                    case 4:
                        eliminarPedido();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                        break;
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    private static void listarPedidos() {
        java.util.List<Pedido> pedidos = pedidoService.listar();

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos cargados.");
            return;
        }

        System.out.println();
        System.out.println("Listado de pedidos:");

        for (Pedido pedido : pedidos) {
            System.out.println(pedido);

            for (DetallePedido detalle : pedido.getDetallesPedido()) {
                if (!detalle.isEliminado()) {
                    System.out.println("   " + detalle);
                }
            }
        }
    }

    private static void crearPedido() {
        System.out.println();
        System.out.println("Crear pedido");

        if (usuarioService.listar().isEmpty()) {
            System.out.println("Primero debe cargar un usuario.");
            return;
        }

        if (productoService.listar().isEmpty()) {
            System.out.println("Primero debe cargar un producto.");
            return;
        }

        listarUsuarios();

        Long usuarioId = leerLong("Id usuario: ");
        FormaPago formaPago = seleccionarFormaPago();

        Pedido pedido = null;

        try {
            pedido = pedidoService.crear(usuarioId, formaPago);

            boolean seguirAgregando = true;

            while (seguirAgregando) {
                listarProductos();

                Long productoId = leerLong("Id producto: ");
                Integer cantidad = leerEntero("Cantidad: ");

                pedidoService.agregarDetalle(pedido, productoId, cantidad);

                String respuesta = leerTexto("Desea agregar otro producto? S/N: ");

                if (!respuesta.equalsIgnoreCase("S")) {
                    seguirAgregando = false;
                }
            }

            pedidoService.guardarPedido(pedido);

            System.out.println("Pedido creado correctamente. Id generado: " + pedido.getId());
            System.out.println("Total: " + pedido.getTotal());

        } catch (RuntimeException e) {
            pedidoService.cancelarCreacionPedido(pedido);
            System.out.println("Error al crear pedido: " + e.getMessage());
        }
    }

    private static void actualizarPedido() {
        listarPedidos();

        Long id = leerLong("Ingrese el id del pedido a actualizar: ");
        Estado estado = seleccionarEstado();
        FormaPago formaPago = seleccionarFormaPago();

        Pedido pedido = pedidoService.actualizarEstadoYFormaPago(id, estado, formaPago);

        System.out.println("Pedido actualizado correctamente: " + pedido);
    }

    private static void eliminarPedido() {
        listarPedidos();

        Long id = leerLong("Ingrese el id del pedido a eliminar: ");
        String confirma = leerTexto("Confirma eliminacion? S/N: ");

        if (confirma.equalsIgnoreCase("S")) {
            pedidoService.eliminar(id);
            System.out.println("Pedido eliminado correctamente.");
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    private static Rol seleccionarRol() {
        System.out.println("Seleccione rol:");
        System.out.println("1. ADMIN");
        System.out.println("2. USUARIO");

        int opcion = leerEntero("Opcion: ");

        switch (opcion) {
            case 1:
                return Rol.ADMIN;
            case 2:
                return Rol.USUARIO;
            default:
                throw new DatoInvalidoException("Rol invalido");
        }
    }

    private static Estado seleccionarEstado() {
        System.out.println("Seleccione estado:");
        System.out.println("1. PENDIENTE");
        System.out.println("2. CONFIRMADO");
        System.out.println("3. TERMINADO");
        System.out.println("4. CANCELADO");

        int opcion = leerEntero("Opcion: ");

        switch (opcion) {
            case 1:
                return Estado.PENDIENTE;
            case 2:
                return Estado.CONFIRMADO;
            case 3:
                return Estado.TERMINADO;
            case 4:
                return Estado.CANCELADO;
            default:
                throw new DatoInvalidoException("Estado invalido");
        }
    }

    private static FormaPago seleccionarFormaPago() {
        System.out.println("Seleccione forma de pago:");
        System.out.println("1. TARJETA");
        System.out.println("2. TRANSFERENCIA");
        System.out.println("3. EFECTIVO");

        int opcion = leerEntero("Opcion: ");

        switch (opcion) {
            case 1:
                return FormaPago.TARJETA;
            case 2:
                return FormaPago.TRANSFERENCIA;
            case 3:
                return FormaPago.EFECTIVO;
            default:
                throw new DatoInvalidoException("Forma de pago invalida");
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                int numero = Integer.parseInt(scanner.nextLine());
                return numero;
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero entero.");
            }
        }
    }

    private static Long leerLong(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                Long numero = Long.parseLong(scanner.nextLine());
                return numero;
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero valido.");
            }
        }
    }

    private static Double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                Double numero = Double.parseDouble(scanner.nextLine());
                return numero;
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero valido.");
            }
        }
    }

    private static Boolean leerBoolean(String mensaje) {
        String respuesta = leerTexto(mensaje);
        return respuesta.equalsIgnoreCase("S");
    }

    private static void cargarDatosIniciales() {
        Categoria hamburguesas = categoriaService.crear("Hamburguesas", "Hamburguesas simples y dobles");
        Categoria pizzas = categoriaService.crear("Pizzas", "Pizzas clasicas");
        Categoria bebidas = categoriaService.crear("Bebidas", "Bebidas frias");

        productoService.crear("Hamburguesa simple", "Hamburguesa con queso", 4500.0, 10, "hamburguesa.jpg", true, hamburguesas.getId());
        productoService.crear("Pizza muzzarella", "Pizza grande de muzzarella", 7000.0, 8, "pizza.jpg", true, pizzas.getId());
        productoService.crear("Gaseosa", "Gaseosa 500ml", 1800.0, 20, "gaseosa.jpg", true, bebidas.getId());

        usuarioService.crear("Juan", "Perez", "juan@mail.com", "2911111111", "1234", Rol.USUARIO);
        usuarioService.crear("Admin", "Sistema", "admin@mail.com", "2912222222", "admin", Rol.ADMIN);
    }
}