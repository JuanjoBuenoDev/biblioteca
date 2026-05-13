package com.biblioteca.repository;

import com.biblioteca.entity.EstadoPrestamo;
import com.biblioteca.entity.Prestamo;
import com.biblioteca.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByAutorId(Long autorId);
    List<Prestamo> findByLibro(Long libroId);
    List<Prestamo> findByEstado(EstadoPrestamo estado);
    List<Prestamo> findByFechaFinPrevistaBeforeAndEstado(LocalDate fecha,  EstadoPrestamo estado);
}
