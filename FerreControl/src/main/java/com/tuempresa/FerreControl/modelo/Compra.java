package com.tuempresa.FerreControl.modelo;

import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity @Getter @Setter
@View(
        members =
                "fecha;" +
                "proveedor;" +
                "detalleCompra;" +
                "total;"
)
public class Compra {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCompra;

    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY) @Required
    @DescriptionsList(descriptionProperties="nombre")
    private Proveedor proveedor; // Relación N:1 con Proveedor

    @OneToMany(mappedBy="compra", cascade=CascadeType.ALL)
    @ListProperties("producto.nombre, cantidad, costoUnitario, subtotal")
    private Collection<DetalleCompra> detalleCompra;

    @Calculation("sum(detalleCompra.subtotal)")
    private double total; // Calculado
}