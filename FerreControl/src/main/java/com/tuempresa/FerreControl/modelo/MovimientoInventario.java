package com.tuempresa.FerreControl.modelo;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Getter @Setter
@View(
        members =
                "fechaHora;" +
                        "producto;" +
                        "tipo;" +
                        "cantidad;" +
                        "observacion"
)
public class MovimientoInventario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMovimiento;

    private LocalDateTime fechaHora;

    @ManyToOne(fetch = FetchType.LAZY) @Required
    @DescriptionsList(descriptionProperties="nombre")
    private Producto producto; // Relación N:1 con Producto

    @Column(length=50) @Required
    private String tipo; // Tipo: Entrada o Salida (E/S)

    private int cantidad;

    @Column(length=255)
    private String observacion;
}