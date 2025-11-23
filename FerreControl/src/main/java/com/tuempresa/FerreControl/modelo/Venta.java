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
    "cliente;" +
    "tipoComprobante;"
)
public class Venta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVenta;

    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY) @Required
    @DescriptionsList(descriptionProperties="nombre")
    private com.tuempresa.FerreControl.modelo.Cliente cliente;

    @Column(length=30)
    private String tipoComprobante;

    @OneToMany(mappedBy="venta", cascade=CascadeType.ALL)
    @ListProperties("producto.nombre, cantidad, precio, subtotal")
    private Collection<com.tuempresa.FerreControl.modelo.DetalleVenta> detalleVenta;

    @Calculation("sum(detalleVenta.subtotal)")
    private double total;
}