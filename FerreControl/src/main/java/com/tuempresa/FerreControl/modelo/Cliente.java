package com.tuempresa.FerreControl.modelo;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.ws.rs.DefaultValue;

import org.openxava.annotations.*;
import lombok.*;

@Entity
@Getter @Setter
// Vista de la entidad Cliente: se muestran todos los campos relevantes
@View(members=
        "cedula;" +          // ahora la cédula es clave primaria, se ve en el formulario
                "nombres; apellidos;" +
                "telefono; direccion;" +
                "estado;"
)
public class Cliente {

    // La máscara sirve para poner los guiones automáticamente cuando el usuario ingrese los valores
    // Regex: regla escrita con símbolos que sirve para verificar si un texto cumple un formato específico
    // Sirve para validar cédulas, contraseñas, etc., con un formato específico muyayos
    // Una regex es un patrón que permite verificar si un texto tiene el formato correcto
    @Id
    @Mask("###-######-####A")
    @Pattern(
            regexp = "^\\d{3}-\\d{6}-\\d{4}[A-Za-z]$",
            message = "{Cliente.cedula.invalid}" // i18n: mensaje internacionalizable
    )
    @Column(length=20)
    @Required
    @Stereotype("ID") // valida unicidad automáticamente en OpenXava
    private String cedula;

    // Nombres y apellidos pueden repetirse
    @Column(length=50) @Required
    private String nombres;

    @Column(length=50) @Required
    private String apellidos;

    // Teléfono con máscara que refleja automáticamente (505) y guion mientras se escribe
    @Mask("(505) ####-####")
    @DefaultValue("(505) ") // valor inicial visible en el formulario
    @Column(length=14)
    @Required
    private String telefono;

    @Column(length=255)
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Required
    @DefaultValue("Activo")
    private EstadoCliente estado;

    // Advertencia de uso: solo lectura, se muestra como texto informativo
    @ReadOnly
    @Stereotype("TEXT")
    @LabelFormat(LabelFormatType.NO_LABEL)
    @DefaultValue("{Cliente.advertencia}") // i18n
    private String advertencia;
}
