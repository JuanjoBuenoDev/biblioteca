package com.biblioteca.repository;

import com.biblioteca.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreAndApellidos(String nombre, String apellidos);
    List<Autor> findByNacionalidad(String nacionalidad);

}
