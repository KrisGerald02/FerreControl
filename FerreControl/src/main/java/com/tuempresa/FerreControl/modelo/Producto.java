package com.tuempresa.FerreControl.modelo;

import javax.persistence.*;
import javax.validation.constraints.Min;

import org.openxava.annotations.*;
import lombok.*;

@Entity @Getter @Setter
@View(
    members =
        "nombre;" +
        "categoria;" +
        "descripcion;"+
        "marca;"+
        "precioVenta, iva;" +
        "stock, stockMinimo, stockMaximo;" +
        "unidadMedida, estadoStock;"+
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
