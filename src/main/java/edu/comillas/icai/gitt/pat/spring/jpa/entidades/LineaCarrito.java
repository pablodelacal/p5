package edu.comillas.icai.gitt.pat.spring.jpa.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity

public class LineaCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El id del carrito no puede ser nulo")
    @Column(nullable = false)
    private Long idCarrito;

    @NotNull(message = "El id del articulo no puede ser nulo")
    @Column(nullable = false)
    private Long idArticulo;

    @NotNull(message = "El precio total no puede ser nulo")
    @Column(nullable = false)
    private Double precioUnitario;

    @NotNull(message = "El número de unidades no puede ser nulo")
    @Column(nullable = false)
    private Integer numeroUnidades;

    @NotNull(message = "El coste total de la linea no puede ser nulo")
    @Column(nullable = false)
    private Double costeLinea;

    //una linea de carrito pertenee a un carrito
    @ManyToOne(optional = false)
    @JoinColumn(name = "carrito_id")
    @JsonBackReference
    private Carrito carrito;

    public LineaCarrito() {
    }

    public LineaCarrito(Long idCarrito, Long idArticulo, Double precioUnitario, Integer numeroUnidades, Double costeLinea, Carrito carrito) {
        this.idCarrito = idCarrito;
        this.idArticulo = idArticulo;
        this.precioUnitario = precioUnitario;
        this.numeroUnidades = numeroUnidades;
        this.costeLinea = costeLinea;
        this.carrito = carrito;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Long getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(Long idCarrito) {
        this.idCarrito = idCarrito;
    }

    public Double getCosteLinea() {
        return costeLinea;
    }

    public void setCosteLinea(Double costeLinea) {
        this.costeLinea = costeLinea;
    }

    public Integer getNumeroUnidades() {
        return numeroUnidades;
    }

    public void setNumeroUnidades(Integer numeroUnidades) {
        this.numeroUnidades = numeroUnidades;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
