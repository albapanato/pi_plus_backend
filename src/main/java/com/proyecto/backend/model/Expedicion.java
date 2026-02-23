package com.proyecto.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

// LOMBOK
@AllArgsConstructor         // => Constructor con todos los argumentos
@NoArgsConstructor          // => Constructor sin argumentos
@Data                       // => @Getter + @Setter + @ToString + @EqualsAndHashCode + @RequiredArgsConstructor
@ToString(exclude = "usuario")           // Excluir del toString para evitar recursividad
@EqualsAndHashCode(exclude = "usuario")  // Excluir de equals y hashCode para evitar recursividad

// JPA
@Entity
@Table(name = "expediciones")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Expedicion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_recepcion")
    private LocalDateTime fechaRecepcion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @NotBlank(message = "El direccion destino es obligatorio")
    @Size(min = 1, max = 255, message = "La direccion destino no puede tener más de 255 caracteres")
    @Column(name = "direccion_destino", nullable = false, unique = false)
    private String direccionDestino;

    @Min(value = 0, message = "Los paquetes como mínimo es 0")
    @Column(name = "paquetes", unique = false)
    private int paquetes;

    @Min(value = 0, message = "El peso mínimo es 0")
    @Column(name = "peso", unique = false)
    private int peso;

    @Size(min = 1, max = 255, message = "Las notas no puede tener más de 255 caracteres")
    @Column(name = "notas", unique = false)
    private String notas;

    @Enumerated(EnumType.STRING)
    @Column(name="estado", nullable = false)
    private EstadoExpedicion estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @JsonIgnoreProperties("expediciones")
    private Usuario usuario;

}
