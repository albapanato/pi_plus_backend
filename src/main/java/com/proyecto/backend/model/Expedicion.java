package com.proyecto.backend.model;


import io.swagger.v3.oas.annotations.media.Schema;
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
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// LOMBOK
@AllArgsConstructor         // => Constructor con todos los argumentos
@NoArgsConstructor          // => Constructor sin argumentos
@Data                       // => @Getter + @Setter + @ToString + @EqualsAndHashCode + @RequiredArgsConstructor

// SWAGGER
@Schema(description = "Modelo de Expediciones", name="Expedicione")

// JPA
@Entity
@Table(name = "expediciones")
public class Expedicion implements Serializable {

    private static final long serialVersionUID = 1L; 
    
    @Schema(description = "ID único del Expedicione", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true) 
    private int id;
    
    @Schema(description = "Descripción del Expedicione", example = "Ordenador portátil")
    @Column(name = "fecha_creacion", nullable = false, unique = false) 
    private LocalDateTime fechaCreacion;


    @Schema(description = "Fecha recepcion", example = "2026-02-23T12:00:00.000Z")
    @Column(name = "fecha_recepcion", nullable = false, unique = false) 
    private LocalDateTime  fechaRecepcion;

    @Schema(description = "Fecha modificacion", example = "2026-02-23T12:30:00.000Z")
    @Column(name = "fecha_modificacion", nullable = false, unique = false) 
    private LocalDateTime fechaModificacion;

    @Schema(description = "Direccioon de destino de la expedición", example = "Calle Mayor, 3, 03010, alicante, España")
    @NotBlank(message = "El direccion destino es obligatorio")
    @Size(min = 1, max = 255, message = "La direccion destino no puede tener más de 255 caracteres")
    @Column(name = "direccion_destino", nullable = false, unique = false)
    private String direccionDestino;

    @Schema(description = "Cantidad de paquetes que está asignado a una expedicion", example = "10 paquetes")
    @Column(name = "paquetes", nullable = true , unique = false) 
    private Integer paquetes;

    @Schema(description = "Peso total", example = "120 kg")
    @Column(name = "peso", nullable = true , unique = false) 
    private Integer peso;

    @Schema(description = "Notas de una expedicion", example = "Carga fragil, no exponer al sol")
    @Size(min=1, max=255, message = "La notas no puede tener más de 255 caracteres")
    @Column(name = "notas", nullable = false , unique = false) 
    private String notas;

     @Schema(description = "estado de una expedicion", example = "en_transito")
    @Enumerated(EnumType.STRING)
    @Column(name="estado", nullable = false)
    private EstadoExpedicion estado;
    
    @Schema(description = "usuarios en una expedicion", example = "120 kg")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties("expediciones") // Bloquea bucle infinito de relaciones
    private Usuario usuario;
    
    @Schema(description = "cajas en una expedicion", example = "120 kg")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caja_id", nullable = false) 
    private Caja caja;


       
    
    
}
