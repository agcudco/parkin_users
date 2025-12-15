package ec.edu.espe.clientes.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase abstracta que representa un Vehículo en el sistema
 * Patrón: Strategy para diferentes tipos de vehículos
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "vehiculos")
@DiscriminatorColumn(name = "tipo_vehiculo", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Vehiculo {

    @Id
    @ColumnDefault("gen_random_uuid()")
    @Generated
    private UUID id; // Identificador único universal

    @Column(name = "placa", nullable = false, unique = true, length = 10)
    private String placa; // Número de placa del vehículo

    @Column(name = "marca", nullable = false, length = 50)
    private String marca; // Marca del vehículo (Toyota, Honda, etc.)

    @Column(name = "modelo", nullable = false, length = 50)
    private String modelo; // Modelo del vehículo

    @Column(name = "color", length = 30)
    private String color; // Color del vehículo

    @Column(name = "anio_fabricacion", nullable = false)
    private Integer anioFabricacion; // Año de fabricación

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro; // Fecha de registro en el sistema

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona propietario; // Dueño del vehículo (Relación Many-to-One)

    @Column(name = "activo", nullable = false)
    private Boolean activo; // Estado activo/inactivo

    /**
     * Método hook que se ejecuta antes de persistir
     */
    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
        this.activo = true;
    }

    /**
     * Método abstracto para calcular impuesto según tipo de vehículo
     * @return double con el valor del impuesto
     */
    public abstract double calcularImpuesto();
}