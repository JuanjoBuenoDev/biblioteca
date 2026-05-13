package com.biblioteca.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "libros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El isbn no puede estar vacio")
    @Column(unique = true)
    @Pattern(
            regexp = "^(97(8|9))?\\d{9}(\\d|X)$",
            message = "ISBN no válido"
    )
    private String isbn;

    @NotBlank(message = "El titulo no puede estar vacio")
    @Size(max = 60, message = "No puede tener mas de 60 caracteres")
    private String titulo;

    @Size(max = 60,message = "El genero no puede tener mas de 60 caracteres")
    private String genero;

    @Min(1450)
    private Integer anioPublicacion;

    @Min(0)
    private int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    @NotNull
    private Autor autor;
}
