package edu.comillas.icai.gitt.pat.spring.jpa.servicios;

import edu.comillas.icai.gitt.pat.spring.jpa.entidades.Carrito;
import edu.comillas.icai.gitt.pat.spring.jpa.entidades.LineaCarrito;
import edu.comillas.icai.gitt.pat.spring.jpa.modelos.ModeloCarrito;
import edu.comillas.icai.gitt.pat.spring.jpa.modelos.ModeloLineaCarrito;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorios.RepositorioCarrito;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorios.RepositorioLineaCarrito;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {

    private final RepositorioCarrito repositorioCarrito;
    private final RepositorioLineaCarrito repositorioLineaCarrito;


    public CarritoService(RepositorioCarrito repositorioCarrito, RepositorioLineaCarrito repositorioLineaCarrito) {
        this.repositorioCarrito = repositorioCarrito;
        this.repositorioLineaCarrito = repositorioLineaCarrito;
    }

    //Crear carrito
    public Carrito crearCarrito(ModeloCarrito modeloCarrito){
        Optional<Carrito> carritoOptional =
                repositorioCarrito.findCarritoByIdUsuario(modeloCarrito.idUsuario());

        if(carritoOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Carrito carrito = new Carrito(
                new ArrayList<>(),
                0.0,
                modeloCarrito.emailUsuario(),
                modeloCarrito.idUsuario()

        );

        return repositorioCarrito.save(carrito);
    }

    //Obtener todos los carritos
    public Iterable<Carrito> getCarritos(){
        return repositorioCarrito.findAll();
    }

    //Obtener carritos por idCarrito
    public Carrito getCarritoIdCarrito(Long idCarrito){
        Optional<Carrito> carritoOptional =
                repositorioCarrito.findById(idCarrito);

        if(carritoOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return carritoOptional.get();
    }

    //Obtener carritos por idUsuario
    public Carrito getCarritoIdUsuario(Long idUsuario){
        Optional<Carrito> carritoOptional =
                repositorioCarrito.findCarritoByIdUsuario(idUsuario);

        if(carritoOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return carritoOptional.get();
    }

    //Actualizar un carrito
    public Carrito actualizarCarrito(Long idCarrito, ModeloCarrito modeloCarrito){
        Optional<Carrito> carritoOptional =
                repositorioCarrito.findById(idCarrito);

        if(carritoOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        //Como tal, solo deberia de poder actualizar el id de usuario y el email del usuario
        Carrito carrito = carritoOptional.get();
        carrito.setIdUsuario(modeloCarrito.idUsuario());
        carrito.setEmailUsuario(modeloCarrito.emailUsuario());

        return repositorioCarrito.save(carrito);

    }

    //Eliminar un carrito
    public void borrarCarrito(Long idCarrito){
        Optional<Carrito> carritoOptional =
                repositorioCarrito.findById(idCarrito);

        if(carritoOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        repositorioCarrito.delete(carritoOptional.get());
    }

    //Obtener todas las lineas de un carrito
    public List<LineaCarrito> getLineasCarritoId(Long idCarrito){
        Optional<Carrito> carritoOptional =
                repositorioCarrito.findById(idCarrito);

        if(carritoOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return carritoOptional.get().getLineasCarritos();
    }

    //Añadir linea a un carrito
    public Carrito addLineaCarrito(Long idCarrito, ModeloLineaCarrito modeloLineaCarrito){
        Optional<Carrito> carritoOptional =
                repositorioCarrito.findById(idCarrito);

        if(carritoOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Carrito carrito = carritoOptional.get();

        LineaCarrito lineaCarrito = new LineaCarrito(
                idCarrito,
                modeloLineaCarrito.idArticulo(),
                modeloLineaCarrito.precioUnitario(),
                modeloLineaCarrito.numeroUnidades(),
                modeloLineaCarrito.precioUnitario() * modeloLineaCarrito.numeroUnidades(),
                carrito
        );

        carrito.addLineaCarrito(lineaCarrito);
        carrito.recalculaPrecioTotal();

        return repositorioCarrito.save(carrito);
    }

    //Actualizar lineaCarrito
    public Carrito actualizarLineaCarrito(Long idCarrito,
                                          Long idLineaArticulo,
                                          ModeloLineaCarrito modeloLineaCarrito){
        Optional<Carrito> carritoOptional =
                repositorioCarrito.findById(idCarrito);

        if(carritoOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Optional<LineaCarrito> lineaCarritoOptional =
                repositorioLineaCarrito.findById(idLineaArticulo);

        if(lineaCarritoOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        //Ahora ya puedo actualizar la linea de carrito
        Carrito carrito = carritoOptional.get();
        LineaCarrito lineaCarrito = lineaCarritoOptional.get();

        //Corrección del chat importante: hay que comprobar tb si la linea pertenece al carrito!!
        if (!lineaCarrito.getCarrito().getId().equals(idCarrito)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        lineaCarrito.setIdArticulo(modeloLineaCarrito.idArticulo());
        lineaCarrito.setPrecioUnitario(modeloLineaCarrito.precioUnitario());
        lineaCarrito.setNumeroUnidades(modeloLineaCarrito.numeroUnidades());
        lineaCarrito.setCosteLinea(modeloLineaCarrito.precioUnitario() * modeloLineaCarrito.numeroUnidades());

        carrito.recalculaPrecioTotal();
        return repositorioCarrito.save(carrito);
    }

    //Borrar una linea de un carrito
    public Carrito eliminarLineaCarrito(Long idCarrito,
                                        Long idLineaArticulo){
        Optional<Carrito> carritoOptional =
                repositorioCarrito.findById(idCarrito);

        if(carritoOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Optional<LineaCarrito> lineaCarritoOptional =
                repositorioLineaCarrito.findById(idLineaArticulo);

        if(lineaCarritoOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        //Ahora ya puedo actualizar la linea de carrito
        Carrito carrito = carritoOptional.get();
        LineaCarrito lineaCarrito = lineaCarritoOptional.get();

        if (!lineaCarrito.getCarrito().getId().equals(idCarrito)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        carrito.eliminarLineaCarrito(lineaCarrito);
        carrito.recalculaPrecioTotal();

        return repositorioCarrito.save(carrito);

    }

}