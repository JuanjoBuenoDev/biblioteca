package com.biblioteca.service;

import com.biblioteca.entity.Libro;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.repository.AutorRepository;
import com.biblioteca.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public List<Libro> listarTodos() {
        return libroRepository.findAll();
    }

    public Libro buscarPorIsbn(String isbn) {
        if  (!libroRepository.findByIsbn(isbn).isPresent()) {
            throw new ResourceNotFoundException("No existe el libro con el isbn " + isbn);
        }
        return libroRepository.findByIsbn(isbn).get();
    }

    public Libro crear(Libro libro) {
        if (libroRepository.findByIsbn(libro.getIsbn()).isPresent()) {
            throw new ResourceNotFoundException("Libro existente");
        } else if (!autorRepository.existsById(libro.getAutor().getId())) {
            throw new ResourceNotFoundException("No existe el autor con el id " + libro.getAutor().getId());
        }
        return libroRepository.save(libro);
    }

    public Libro actualizar(Long id, Libro libro) {
        if (libroRepository.findById(id).isPresent()) {

        }
    }
}
