package com.tuempresa.FerreControl.modelo;

import java.math.*;
import java.time.*;
import java.util.*;

import javax.persistence.*;

import com.tuempresa.FerreControl.modelo.Cliente;
import com.tuempresa.FerreControl.modelo.DetalleFactura;
import org.openxava.annotations.*;
import org.openxava.model.*;

@Entity
@View(name="SinDetalles", members="anyo, numero, fecha, cliente")
public class Factura extends Identifiable {

    @Column(length=4)
    private int anyo;

    @Column(length=6)
    private int numero;

    @Required
    private LocalDate fecha;

    @ManyToOne(fetch=FetchType.LAZY)
    // SOLUCIÓN AL ERROR: Le decimos a OpenXava qué campo mostrar del Cliente
    @DescriptionsList(descriptionProperties="nombreCompleto")
    private Cliente cliente; // Referencia al cliente

    @ElementCollection
    @ListProperties("producto.descripcion, cantidad, precio, importe")
    private Collection<DetalleFactura> detalles;

    @Money
    private BigDecimal importeTotal;

    // Getters y Setters

    public int getAnyo() {
        return anyo;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Collection<DetalleFactura> getDetalles() {
        return detalles;
    }

    public void setDetalles(Collection<DetalleFactura> detalles) {
        this.detalles = detalles;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }
}