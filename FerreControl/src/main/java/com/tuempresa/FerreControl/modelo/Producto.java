package com.tuempresa.FerreControl.modelo;

import javax.persistence.*;
import javax.validation.constraints.Min;

import org.openxava.annotations.*;
import lombok.*;

import java.time.LocalDate;

@Entity @Getter @Setter
@View(
    members =

            /*
        "Nuevo Producto { nombre, descripcion }" +
        "Características { categoria, marca, unidadMedida}" +
        "Fechas { fechaExpedicion, fechaVencimiento }" +
        "Inventario { stockMinimo, stockMaximo, estadoStock}" +
        "Costo Unitario { precioVenta, iva}" +
        "imagen;"
             */
        "nombre;" +
        "descripcion;"+
        "categoria, marca, unidadMedida;" +
        "fechaExpedicion, fechaVencimiento;" +
        "stockMinimo, stockMaximo, estadoStock;" +
        "precioVenta, iva;" +
        "imagen;"
)
public class Producto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Hidden
    private int idProducto;

    @Column(length=50) @Required
    private String nombre;

    @Column(length=100)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties="nombre")
    @Required
    private CategoriaProducto categoria;

    @Column(length=20) @Required
    private String marca;

    @Column(length=10)
    @Required
    private LocalDate fechaExpedicion;

    @Column(length=10)
    private LocalDate fechaVencimiento;


    @Money // Le da formato de moneda (dos decimales, separador de miles, etc.)
    @Column(precision = 10, scale = 2) // Asegura que el campo tenga precisión de moneda en la DB
    @DefaultStringValue("C$ ") // Muestra el símbolo "C$" al iniciar la entrada (puede variar según el navegador)
    @Min(0)
    private double precioVenta;

    @Min(0)
    private double iva;

    @Min(0)
    private int stock;

    //add new stock the user could input an specific range
    @Min(0)
    private int stockMinimo;

    @Min(0)
    private int stockMaximo;

    @Enumerated(EnumType.STRING)
    @Required
    private UnidadMedida unidadMedida;

    @Enumerated(EnumType.STRING)
    @Required
    private EstadoStock estadoStock;

    @Files
    @Column(length = 32)
    String imagen;
}
