package com.biblioteca.service;

import com.biblioteca.entity.Autor;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AutorService {
    private final AutorRepository autorRepository;
    private final Formato formato;

    @Transactional(readOnly = true)
    public List<Autor> listarTodos() {
        List<Autor> autores = new ArrayList<>(autorRepository.findAll());
        autores.sort(Comparator.comparing(Autor::getApellidos));
        return autores;
    }

    @Transactional(readOnly = true)
    public Autor buscarPorId(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado con el id: " + id));
    }

    @Transactional
    public Autor crear(Autor autor) {
        if (autorRepository.findByNombreAndApellidos(autor.getNombre(), autor.getApellidos()).isPresent()) {
            throw new IllegalStateException("Autor ya existe");
        }
        if (autor.getNacionalidad() != null && !autor.getNacionalidad().isBlank()) {
            autor.setNacionalidad(formato.formatoNacionalidad(autor.getNacionalidad()));
        }
        return autorRepository.save(autor);
    }

    @Transactional
    public Autor actualizar(Long id, Autor autor) {
        Autor a = buscarPorId(id);
        a.setApellidos(autor.getApellidos());
        a.setNombre(autor.getNombre());
        if (autor.getNacionalidad() != null) {
            a.setNacionalidad(formato.formatoNacionalidad(autor.getNacionalidad()));
        }
        a.setFechaNacimiento(autor.getFechaNacimiento());

        return autorRepository.save(a);
    }

    @Transactional
    public void eliminar(Long id) {
        Autor a = buscarPorId(id);
        if (!a.getLibros().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar un autor con libros asociados");
        }
        autorRepository.delete(a);
    }

    @Transactional(readOnly = true)
    public List<Autor> buscarPorNacionalidad(String nacionalidad) {
        return autorRepository.findByNacionalidad(formato.formatoNacionalidad(nacionalidad));
    }
}
