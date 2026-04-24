package edu.comillas.icai.gitt.pat.spring.jpa.controladores;

import edu.comillas.icai.gitt.pat.spring.jpa.entidades.Carrito;
import edu.comillas.icai.gitt.pat.spring.jpa.entidades.LineaCarrito;
import edu.comillas.icai.gitt.pat.spring.jpa.modelos.ModeloCarrito;
import edu.comillas.icai.gitt.pat.spring.jpa.modelos.ModeloLineaCarrito;
import edu.comillas.icai.gitt.pat.spring.jpa.servicios.CarritoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    //Empiezo con mis endpoints
    //Creo un carrito
    @PostMapping("/api/carritos")
    @ResponseStatus(HttpStatus.CREATED)
    public Carrito crear(@Valid @RequestBody ModeloCarrito modeloCarrito,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos incorrectos");
        }

        return carritoService.crearCarrito(modeloCarrito);
    }

    //Obtener todos los carritos
    @GetMapping("/api/carritos")
    public Iterable<Carrito> getCarritos() {
        return carritoService.getCarritos();
    }

    //Obtener carrito por idCarrito
    @GetMapping("/api/carritos/{idCarrito}")
    public Carrito getCarritoIdCarrito(@PathVariable Long idCarrito) {
        return carritoService.getCarritoIdCarrito(idCarrito);
    }

    //Obtener carrito por idUsuario
    @GetMapping("/api/carritos/usuario/{idUsuario}")
    public Carrito getCarritoIdUsuario(@PathVariable Long idUsuario) {
        return carritoService.getCarritoIdUsuario(idUsuario);
    }

    //Actualizar carrito
    @PutMapping("/api/carritos/{idCarrito}")
    public Carrito actualizarCarrito(@PathVariable Long idCarrito,
                                     @Valid @RequestBody ModeloCarrito modeloCarrito,
                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos incorrectos");
        }

        return carritoService.actualizarCarrito(idCarrito, modeloCarrito);
    }

    //Borrar carrito
    @DeleteMapping("/api/carritos/{idCarrito}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarCarrito(@PathVariable Long idCarrito) {
        carritoService.borrarCarrito(idCarrito);
    }

    //Obtener las lineas de un carrito
    @GetMapping("/api/carritos/{idCarrito}/lineas")
    public List<LineaCarrito> getLineasCarrito(@PathVariable Long idCarrito) {
        return carritoService.getLineasCarritoId(idCarrito);
    }

    //Añadir linea a carrito
    @PostMapping("/api/carritos/{idCarrito}/lineas")
    @ResponseStatus(HttpStatus.CREATED)
    public Carrito addLineaCarrito(@PathVariable Long idCarrito,
                                   @Valid @RequestBody ModeloLineaCarrito modeloLineaCarrito,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos incorrectos");
        }

        return carritoService.addLineaCarrito(idCarrito, modeloLineaCarrito);
    }

    //Actualizar linea carrito
    @PutMapping("/api/carritos/{idCarrito}/lineas/{idLineaArticulo}")
    public Carrito actualizarLineaCarrito(@PathVariable Long idCarrito,
                                          @PathVariable Long idLineaArticulo,
                                          @Valid @RequestBody ModeloLineaCarrito modeloLineaCarrito,
                                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos incorrectos");
        }

        return carritoService.actualizarLineaCarrito(idCarrito, idLineaArticulo, modeloLineaCarrito);
    }

    //Borrar linea carrito
    @DeleteMapping("/api/carritos/{idCarrito}/lineas/{idLineaArticulo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarLineaCarrito(@PathVariable Long idCarrito,
                                   @PathVariable Long idLineaArticulo) {
        carritoService.eliminarLineaCarrito(idCarrito, idLineaArticulo);
    }

}