package edu.comillas.icai.gitt.pat.spring.jpa.entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El id del usuario es obligatorio")
    @Column(nullable = false)
    private Long idUsuario;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email es incorrecto")
    @Column(nullable = false)
    private String emailUsuario;

    @NotNull(message = "El precio total no puede ser nulo")
    @Column(nullable = false)
    private Double totalPrecio;

    //1 carrito tiene n lineas d ecarrito
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<LineaCarrito> lineasCarritos = new ArrayList<>();

    public Carrito(List<LineaCarrito> lineasCarritos, Double totalPrecio, String emailUsuario, Long idUsuario) {
        this.lineasCarritos = lineasCarritos;
        this.totalPrecio = totalPrecio;
        this.emailUsuario = emailUsuario;
        this.idUsuario = idUsuario;
    }

    public Carrito(){}

    public void setLineasCarritos(List<LineaCarrito> lineaCarritos) {
        this.lineasCarritos = lineaCarritos;
    }

    public List<LineaCarrito> getLineasCarritos() {
        return lineasCarritos;
    }

    public Double getTotalPrecio() {
        return totalPrecio;
    }

    public void setTotalPrecio(Double totalPrecio) {
        this.totalPrecio = totalPrecio;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //Hago los metodos que necesito
    //Añadir una linea de carrito
    public void addLineaCarrito(LineaCarrito lineaCarrito){
        lineaCarrito.setCarrito(this);
        this.lineasCarritos.add(lineaCarrito);
    }

    //Recalcular el precio total del carrito
    public void recalculaPrecioTotal(){
        double total = 0.0;
        for(LineaCarrito lineaCarrito: lineasCarritos){
            total += lineaCarrito.getPrecioUnitario() * lineaCarrito.getNumeroUnidades();
        }
        this.totalPrecio = total;
    }

    //Borrar una linea de carrito
    public void eliminarLineaCarrito(LineaCarrito lineaCarrito){
        this.lineasCarritos.remove(lineaCarrito);
    }


}
