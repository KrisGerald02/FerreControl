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
        "precioVenta, iva;" +
        "stock, stockMinimo, stockMaximo;" +
        "unidadMedida, estadoStock;"
)
public class Producto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Hidden
    private int idProducto;

    @Column(length=50) @Required
    private String nombre;

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
