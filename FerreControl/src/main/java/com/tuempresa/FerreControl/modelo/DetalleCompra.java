package com.tuempresa.FerreControl.modelo;

import javax.persistence.*;
import javax.validation.constraints.Min;

import org.openxava.annotations.*;
import lombok.*;

@Entity @Getter @Setter
public class DetalleCompra {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Hidden
    private int idDetalleCompra;

    @ManyToOne(fetch = FetchType.LAZY) @Required
    @JoinColumn(name="COMPRA_idCompra", nullable=false)
    private com.tuempresa.FerreControl.modelo.Compra compra; // Relación N:1 con Compra (FK)

    @ManyToOne(fetch = FetchType.LAZY) @Required
    @DescriptionsList(descriptionProperties="nombre")
    private Producto producto;

    @Min(1)
    private int cantidad;

    @Min(0)
    private double costoUnitario;

    @Stereotype("MONEY")
    @Depends("cantidad, costoUnitario")
    public double getSubtotal() {
        return cantidad * costoUnitario;
    }
}