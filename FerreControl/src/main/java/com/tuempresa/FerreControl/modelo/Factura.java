package com.tuempresa.FerreControl.modelo;

import org.openxava.annotations.*;
import org.openxava.model.Identifiable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;

@Entity
@View(
        members =
                "anyo, numero;" +
                        "fecha, cliente;" +
                        "detalles { detalles }" +
                        "importeTotal"
)
public class Factura extends Identifiable {

    @Column(length = 4)
    private int anyo;

    @Column(length = 6)
    private int numero;

    @Required
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombreCompleto")
    private Cliente cliente;

    @ElementCollection
    @ListProperties("producto.nombre, cantidad, precio, importe")
    private Collection<DetalleFactura> detalles;

    @ReadOnly
    @Money
    private BigDecimal importeTotal;

    // --------- CÁLCULO AUTOMÁTICO DEL TOTAL ---------
    @PrePersist
    @PreUpdate
    private void calcularImporteTotal() {
        if (detalles == null || detalles.isEmpty()) {
            importeTotal = BigDecimal.ZERO;
            return;
        }

        BigDecimal total = BigDecimal.ZERO;
        for (DetalleFactura d : detalles) {
            if (d != null && d.getImporte() != null) {
                total = total.add(d.getImporte());
            }
        }
        importeTotal = total;
    }
    // ------------------------------------------------

    // --------- GETTERS / SETTERS BÁSICOS ---------

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

    // ---------- CAMPOS DE APOYO PARA REPORTES / LISTADOS ----------

    /* Número de factura formateado, por ejemplo 2025-000001 */
    @Transient
    public String getNumeroFactura() {
        if (anyo == 0) {
            return String.valueOf(numero);
        }
        return anyo + "-" + String.format("%06d", numero);
    }

    /* Fecha como java.util.Date (Jasper trabaja mejor con este tipo) */
    @Transient
    public Date getFechaDate() {
        if (fecha == null) return null;
        return Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /* Nombre completo del cliente para el listado */
    @Transient
    public String getClienteNombre() {
        return cliente != null ? cliente.getNombreCompleto() : "";
    }

    @Transient
    @Money
    public BigDecimal getImporteTotalConIVA() {
        if (importeTotal == null) return BigDecimal.ZERO;

        // IVA 15% (ajusta el porcentaje si usas otro)
        BigDecimal iva = new BigDecimal("0.15");
        BigDecimal montoIVA = importeTotal.multiply(iva);

        return importeTotal.add(montoIVA);
    }
}
