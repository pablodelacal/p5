package edu.comillas.icai.gitt.pat.spring.jpa.modelos;

import jakarta.validation.constraints.*;

public record ModeloLineaCarrito(
        @NotNull(message = "El id de articulo no puede ser nulo")
        Long idArticulo,

        @NotNull(message = "El precio unitario no puede ser nulo")
        @Positive(message = "El precio no puede ser negativo ni cero")
        Double precioUnitario,

        @NotNull(message = "El numero de unidades de la linea no puede ser nulo")
        @Positive(message = "El numero de unidades de la linea no puede ser negativo")
        Integer numeroUnidades
){}