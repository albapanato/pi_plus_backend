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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;
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
@Table(name = "terminales_pago")
public class Terminal implements Serializable {
    
    public enum Estado {
        en_transito,
        pendiente_revision,
        operativo,
        pendiente_laboratorio,
        nivel_1
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
    @Column(name = "numero_serie", nullable = false, unique = false) 
    private String numeroSerie;

    @Schema(description = "Email del usuario", example = "juan@balmis.com")
    @NotBlank(message = "El email es obligatorio")
    @Size(min=1, max=150, message = "El email no puede tener más de 150 caracteres")
    @Column(name = "modelo", nullable = false, unique = false) 
    private String modelo;

    @Schema(description = "Password del usuario encriptada", example = "$2a$10$fzcGgF.8xODz7ptkmZC")
    @NotBlank(message = "El password es obligatorio")
    @Column(name = "marca", nullable = false, unique = false) 
    private String marca;

    
    @Schema(description = "Tipo de estado del terminal", example = "en_transito")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @Schema(description = "Notas terminal", example = "Cable tipo C para cargador ")
    @Column(name = "notas", nullable = true, unique = false) 
    private String notas;

    @Schema(description = "Fecha ingreso", example = "true")
    @Column(name = "fecha_ingreso", nullable = false, unique = false) 
    private Date fechaIngreso;

    @Schema(description = "Fecha creacion", example = "true")
    @Column(name = "fecha_creacion", nullable = false, unique = false) 
    private Date fechaCreacion;

    // @Schema(description = "ID único caja", example = "1")
    // @Column(name = "id_caja", nullable = true)
    // private Integer idCaja;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_caja") 
    @JsonIgnoreProperties("terminales_pago")
    private Caja caja;

    // @OneToMany(mappedBy = "terminal", cascade = CascadeType.ALL)
    // private Set<Expedicion> expediciones = new HashSet<>();
       
    
    
}
