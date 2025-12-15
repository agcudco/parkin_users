package ec.edu.espe.clientes.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Clase que representa un Auto Familiar
 * Hereda de Vehiculo e implementa cálculo de impuesto específico
 */
@Entity
@Table(name = "autos_familiares")
@DiscriminatorValue("AUTO_FAMILIAR")
@PrimaryKeyJoinColumn(name = "vehiculo_id")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class AutoFamiliar extends Vehiculo {

    @Column(name = "numero_puertas", nullable = false)
    private Integer numeroPuertas; // Número de puertas (3, 5, etc.)

    @Column(name = "capacidad_maletero")
    private Integer capacidadMaletero; // Capacidad del maletero en litros

    @Column(name = "tiene_aire_acondicionado")
    private Boolean tieneAireAcondicionado; // Si tiene aire acondicionado

    @Column(name = "tipo_combustible", length = 20)
    private String tipoCombustible; // Tipo: gasolina, diesel, eléctrico, híbrido

    /**
     * Implementación concreta del cálculo de impuesto para autos familiares
     * Fórmula basada en año y tipo de combustible
     * @return double valor del impuesto
     */
    @Override
    public double calcularImpuesto() {
        int antiguedad = java.time.Year.now().getValue() - this.getAnioFabricacion();
        double impuestoBase = 0.0;

        // Impuesto base según antigüedad
        if (antiguedad <= 5) {
            impuestoBase = 400.0;
        } else if (antiguedad <= 10) {
            impuestoBase = 300.0;
        } else {
            impuestoBase = 200.0;
        }



        return Math.max(impuestoBase, 100.0); // Impuesto mínimo de 100
    }
}