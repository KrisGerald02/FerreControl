package com.tuempresa.FerreControl.modelo;

import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity @Getter @Setter
@View(
        members =
                "nombre;" +
                        "telefono;" +
                        "direccion;"
)
public class Proveedor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProveedor;

    @Column(length=50) @Required
    private String nombre;

    @Column(length=15)
    private String telefono;

    @Column(length=255)
    private String direccion;
}