package com.proyecto.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// LOMBOK
@AllArgsConstructor         // => Constructor con todos los argumentos
@NoArgsConstructor          // => Constructor sin argumentos
@Data                       // => @Getter + @Setter + @ToString + @EqualsAndHashCode + @RequiredArgsConstructor

// SWAGGER
@Schema(description = "Modelo de Usuario", name="Usuario")

// JPA
@Entity
@Table(name = "usuarios")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Usuario implements Serializable {

     public enum Rol {
        trabajador_almacen,
        tecnico,
        logistica,
        administrador
    }

    private static final long serialVersionUID = 1L; 
    
    @Schema(description = "ID único del usuario", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true) 
    private int id;
    
    @Schema(description = "Nombre del usuario", example = "Juan")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min=1, max=100, message = "El nombre no puede tener más de 100 caracteres")
    @Column(name = "nombre", nullable = false, unique = false) 
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "Juan")
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min=1, max=100, message = "El apellido no puede tener más de 100 caracteres")
    @Column(name = "apellido", nullable = false, unique = false) 
    private String apellido;

    @Schema(description = "Identificacion usuario para hacer login", example = "juan32")
   @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min=1, max=60, message = "El nombre de usuario no puede tener más de 60 caracteres")
    @Column(name = "nombre_usuario", nullable = false) 
    private String nombreUsuario;

   @NotBlank(message = "La contraseña es obligatorio")
    @Size(min=1, max=255, message = "La contraseña no puede tener más de 255 caracteres")
    @Column(name = "hash_password", nullable = false) 
    private String hashPassword;

    @Schema(description = "Rol trabajador", example = "administrador")
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;

    @Schema(description = "Puesto donde trabaja el usuario", example = "Almacén")
    @NotBlank(message = "El puesto es obligatorio")
    @Size(min=1, max=100, message = "El puesto no puede tener más de 100 caracteres")
    @Column(name = "lugar_trabajo", nullable = false, unique = false) 
    private String lugarTrabajo;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("usuario")  
    private Set<Expedicion> expediciones = new HashSet<>();
 
    
}
