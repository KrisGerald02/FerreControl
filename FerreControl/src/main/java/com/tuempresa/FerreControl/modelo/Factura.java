package com.tuempresa.FerreControl.modelo;

import org.openxava.annotations.*;
import org.openxava.model.Identifiable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@View(
        members =
                "anyo, numero;" +
                        "fecha, cliente;" +
                        "detalles { detalles };" +
                        "subtotal, ivaCalculado, importeTotal;" +
                        "montoPagado, vuelto"
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
    @Required
    private Cliente cliente;

    @ElementCollection(fetch = FetchType.EAGER)
    @ListProperties("producto.nombre, cantidad, precio, descuentoPorcentaje, importe")
    private Collection<DetalleFactura> detalles;

    @ReadOnly
    @Money
    private BigDecimal subtotal;

    @ReadOnly
    @Money
    private BigDecimal ivaCalculado;

    @ReadOnly
    @Money
    private BigDecimal importeTotal;

    @Money
    private BigDecimal montoPagado = BigDecimal.ZERO;

    @ReadOnly
    @Money
    private BigDecimal vuelto;

    private String tipoMoneda = "C$"; // Córdobas

    // --------- CÁLCULO AUTOMÁTICO ---------
    @PrePersist
    @PreUpdate
    private void calcularImportes() {
        if (detalles == null || detalles.isEmpty()) {
            subtotal = BigDecimal.ZERO;
            ivaCalculado = BigDecimal.ZERO;
            importeTotal = BigDecimal.ZERO;
            vuelto = BigDecimal.ZERO;
            return;
        }

        // Calcular subtotal
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleFactura d : detalles) {
            if (d != null && d.getImporte() != null) {
                total = total.add(d.getImporte());
            }
        }
        subtotal = total.setScale(2, RoundingMode.HALF_UP);

        // Calcular IVA (15%)
        BigDecimal tasaIVA = new BigDecimal("0.15");
        ivaCalculado = subtotal.multiply(tasaIVA).setScale(2, RoundingMode.HALF_UP);

        // Calcular total con IVA
        importeTotal = subtotal.add(ivaCalculado).setScale(2, RoundingMode.HALF_UP);

        // Calcular vuelto
        if (montoPagado != null && montoPagado.compareTo(importeTotal) >= 0) {
            vuelto = montoPagado.subtract(importeTotal).setScale(2, RoundingMode.HALF_UP);
        } else {
            vuelto = BigDecimal.ZERO;
        }
    }
    // ------------------------------------------------

    // --------- GETTERS / SETTERS ---------

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

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIvaCalculado() {
        return ivaCalculado;
    }

    public void setIvaCalculado(BigDecimal ivaCalculado) {
        this.ivaCalculado = ivaCalculado;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public BigDecimal getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(BigDecimal montoPagado) {
        this.montoPagado = montoPagado;
        calcularImportes(); // Recalcular vuelto cuando cambia el monto pagado
    }

    public BigDecimal getVuelto() {
        return vuelto;
    }

    public void setVuelto(BigDecimal vuelto) {
        this.vuelto = vuelto;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    // ---------- CAMPOS TRANSIENTES PARA REPORTES ----------

    @Transient
    public String getNumeroFactura() {
        if (anyo == 0) {
            return String.valueOf(numero);
        }
        return anyo + "-" + String.format("%06d", numero);
    }

    @Transient
    public Date getFechaDate() {
        if (fecha == null) return null;
        return Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Transient
    public String getClienteNombre() {
        return cliente != null ? cliente.getNombreCompleto() : "";
    }

    @Transient
    public String getSubtotalFormato() {
        return formatoMoneda(subtotal);
    }

    @Transient
    public String getIvaFormato() {
        return formatoMoneda(ivaCalculado);
    }

    @Transient
    public String getImporteTotalFormato() {
        return formatoMoneda(importeTotal);
    }

    @Transient
    public String getVueltoFormato() {
        return formatoMoneda(vuelto);
    }

    @Transient
    public String getMontoPagadoFormato() {
        return formatoMoneda(montoPagado);
    }

    private String formatoMoneda(BigDecimal monto) {
        if (monto == null) return tipoMoneda + " 0.00";
        return tipoMoneda + " " + String.format("%,.2f", monto);
    }

    @Transient
    public boolean isPagada() {
        return montoPagado != null && montoPagado.compareTo(importeTotal) >= 0;
    }

    @Transient
    public BigDecimal getMontoRestante() {
        if (montoPagado == null) return importeTotal;
        if (importeTotal == null) return BigDecimal.ZERO;

        BigDecimal restante = importeTotal.subtract(montoPagado);
        return restante.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : restante.setScale(2, RoundingMode.HALF_UP);
    }
}
