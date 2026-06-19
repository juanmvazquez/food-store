# 🍔 Food Store - Sistema de Pedidos

**Trabajo Práctico Integrador (TPI) — Programación II**  
Tecnicatura Universitaria en Programación (TUP) — 2do Cuatrimestre 2026

---

## Descripción

**Food Store** es una aplicación de consola desarrollada en Java que permite gestionar un sistema de pedidos para una tienda de comidas. El sistema cubre el ciclo completo de gestión: categorías, productos, usuarios y pedidos, con lógica de negocio integrada (control de stock, cálculo de totales, roles de usuario, etc.).

## Video de presentación

[![Ver presentación en YouTube](https://img.shields.io/badge/YouTube-Ver%20presentaci%C3%B3n-red?logo=youtube)](https://www.youtube.com/watch?v=cPMjVyteOKg)

---

## Funcionalidades

### Categorías
- Crear, listar, editar y eliminar categorías de productos (eliminación lógica).
- Validación de nombres duplicados.

### Productos
- Alta, consulta, modificación y baja de productos.
- Atributos: nombre, precio, stock, ruta de imagen, disponibilidad y categoría asignada.

### Usuarios
- Gestión de usuarios con roles (`ADMIN` / `USUARIO`).
- Validación de emails duplicados y campos obligatorios.

### Pedidos
- Creación de pedidos con múltiples ítems (detalle de pedido).
- Descuento automático de stock al agregar productos; restauración si se cancela la creación.
- Cálculo de total por ítem (`precio × cantidad`) y total general del pedido.
- Actualización de estado del pedido y forma de pago.
- Eliminación lógica de pedidos.

---

## Tecnologías utilizadas

| Tecnología | Detalle |
|---|---|
| **Java 21** | Lenguaje principal |
| **Maven** | Gestión de proyecto y build |
| **IntelliJ IDEA** | IDE de desarrollo |
| **Java Collections** | `ArrayList` para persistencia en memoria |
| **POO** | Herencia, interfaces, enums, excepciones personalizadas |

---

## Estructura del proyecto

```
tpi/
├── pom.xml
└── src/main/java/org/programacionII/
    ├── Main.java                    # Punto de entrada y menú de consola
    ├── entities/
    │   ├── Base.java                # Clase base con id y eliminado
    │   ├── Categoria.java
    │   ├── Producto.java
    │   ├── Usuario.java
    │   ├── Pedido.java
    │   ├── DetallePedido.java
    │   └── enums/
    │       ├── Rol.java             # ADMIN, USUARIO
    │       ├── Estado.java          # Estados del pedido
    │       └── FormaPago.java       # Formas de pago
    ├── services/
    │   ├── CategoriaService.java
    │   ├── ProductoService.java
    │   ├── UsuarioService.java
    │   └── PedidoService.java
    ├── interfaces/
    │   └── Calculable.java          # calcularTotal()
    └── exception/
        ├── DatoInvalidoException.java
        ├── EntidadDuplicadaException.java
        ├── EntidadNoEncontradaException.java
        └── StockInvalidoException.java
```

---

## Cómo ejecutar el proyecto

### Requisitos previos
- Java 21 o superior instalado
- Maven instalado (opcional, se puede ejecutar desde el IDE)

### Desde IntelliJ IDEA
1. Abrir el proyecto como proyecto Maven.
2. Ejecutar la clase `Main.java`.

### Desde la terminal con Maven
```bash
mvn compile exec:java -Dexec.mainClass="org.programacionII.Main"
```

---

## Persistencia

El sistema utiliza **persistencia en memoria** mediante `ArrayList`. Los datos no se guardan entre ejecuciones, pero al iniciar la aplicación se cargan datos de prueba (categorías, productos y usuarios de ejemplo).

---

## Integrantes del equipo

> *Vazquez, Juan Manuel*

---

## Institución

Tecnicatura Universitaria en Programación — TUP  
Programación II — 2do Cuatrimestre 2026
