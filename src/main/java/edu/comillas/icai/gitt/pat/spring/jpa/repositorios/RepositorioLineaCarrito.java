package edu.comillas.icai.gitt.pat.spring.jpa.repositorios;

import edu.comillas.icai.gitt.pat.spring.jpa.entidades.LineaCarrito;
import org.springframework.data.repository.CrudRepository;

import java.util.*;


public interface RepositorioLineaCarrito extends CrudRepository<LineaCarrito, Long> {
}
