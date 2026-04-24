package edu.comillas.icai.gitt.pat.spring.jpa.modelos;

import jakarta.validation.constraints.*;

public record ModeloCarrito(

        @NotNull(message = "El id de usuario no puede ser nulo")
        Long idUsuario,

        @NotBlank(message = "El email del pedido no puede estar vacio")
        @Email(message = "El formato del email es incorrecto")
        String emailUsuario
){}