package com.biblioteca.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    @Column(unique = true, nullable = false)
    @Size(min = 4, max = 40)
    private String username;

    @NotBlank(message = "El password no puede estar vacio")
    @Size(min = 6, message = "La contraseña debe tener un minimo de 6 caracteres")
    private String password;

    @NotBlank(message = "Tiene que haber un email")
    @Email(message = "El email debe ser valido")
    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    private boolean activo;
}
