package com.tuempresa.FerreControl.modelo;

import lombok.*;
import org.openxava.annotations.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@View(
        members =
                "datosFactura {" +
                        "    numeroFactura; fecha; tipoPago;" +
                        "}" +
                        "venta;" +
                        "cliente;" +
                        "totales {" +
                        "    subtotal; iva; total;" +
                        "}"
)
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFactura;

    // Número de factura único
    @Column(length = 20, unique = true)
    @Required
    @Stereotype("LABEL")
    private String numeroFactura;

    // Fecha de emisión
    @Required
    private LocalDate fecha = LocalDate.now();

    // Tipo de pago o comprobante adicional
    @Column(length=30)
    private String tipoPago;

    // Relación 1:1 con Venta
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VENTA_idVenta", nullable = false)
    @Required
    @ReferenceView("Simple")
    private Venta venta;

    // Cliente derivado de la venta
    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombres, apellidos")
    @ReadOnly
    public Cliente getCliente() {
        return venta != null ? venta.getCliente() : null;
    }

    // Subtotal deriva de la venta
    @ReadOnly
    @Stereotype("MONEY")
    public double getSubtotal() {
        return venta != null ? venta.getTotal() : 0;
    }

    // IVA calculado automáticamente (15% ejemplo)
    @ReadOnly
    @Stereotype("MONEY")
    public double getIva() {
        return getSubtotal() * 0.15;
    }

    // Total final
    @ReadOnly
    @Stereotype("MONEY")
    public double getTotal() {
        return getSubtotal() + getIva();
    }
}

