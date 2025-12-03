package com.tuempresa.FerreControl.modelo;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.openxava.annotations.*;
import lombok.*;

@Entity @Getter @Setter
@View(
    members =
        "nombre;" +
        "correo,telefono;" +
        "departamento,direccion;"

)
public class Proveedor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProveedor;

    @Column(length=50, unique = true) @Required
    private String nombre;


    @Column(length=40, unique = true) @Required
    private String correo;


    @Mask("####-####")
    @Column(length=8)
    @Required
    private String telefono;

    @Enumerated(EnumType.STRING) // Guarda el nombre (ej: MANAGUA) en la BD en lugar del índice
    @Required
    private DepartamentoNicaragua departamento;

    @Column(length=300)
    private String direccion;


    // 1..* para que OpenXava maneje bien proveedor - compra
    @OneToMany(mappedBy="proveedor")
    private java.util.Collection<Compra> compras;
}
