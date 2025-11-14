
package com.tuempresa.FerreControl.modelo;

import javax.persistence.*;
import javax.ws.rs.DefaultValue;

import org.openxava.annotations.*;
import lombok.*;

@Entity @Getter @Setter
@View(members=
        "nombre; telefono; direccion;" +
                "estado;" +
                "advertencia"
)
public class Cliente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;

    @Column(length=50) @Required
    private String nombre;

    @Column(length=15)
    private String telefono;

    @Column(length=255)
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Required
    @DefaultValue("Activo")
    private EstadoCliente estado;

    @ReadOnly
    @Stereotype("TEXT")
    @LabelFormat(LabelFormatType.NO_LABEL)
    @DefaultValue("NOTA IMPORTANTE: No es común borrar clientes del maestro.\nUse el estado Inactivo en lugar de eliminarlos.")
    private String advertencia;
}
