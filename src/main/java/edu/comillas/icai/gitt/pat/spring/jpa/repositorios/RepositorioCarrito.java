package edu.comillas.icai.gitt.pat.spring.jpa.repositorios;

import edu.comillas.icai.gitt.pat.spring.jpa.entidades.Carrito;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepositorioCarrito extends CrudRepository<Carrito, Long> {

    Optional<Carrito> findCarritoByIdUsuario(Long idUsuario);
}
