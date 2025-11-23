package com.tuempresa.FerreControl.modelo;

import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity @Getter @Setter
public class Usuario {

    @Id @Column(length=20)
    private String idUsuario;

    @Column(length=50) @Required
    private String nombre;

    @Column(length=100) @Required
    @Stereotype("PASSWORD")
    private String password;

    @ManyToOne @Required
    @DescriptionsList
    private com.tuempresa.FerreControl.modelo.Rol rol;
}