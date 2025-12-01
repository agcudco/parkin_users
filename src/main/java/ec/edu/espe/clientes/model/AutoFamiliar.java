package ec.edu.espe.clientes.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Clase que representa un Auto Familiar
 * Hereda de Vehiculo e implementa cálculo de impuesto específico
 */
@Entity
@Table(name = "autos_familiares")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
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

        // Ajustes por tipo de combustible
        switch (this.tipoCombustible.toLowerCase()) {
            case "eléctrico":
                impuestoBase *= 0.5; // 50% de descuento
                break;
            case "híbrido":
                impuestoBase *= 0.7; // 30% de descuento
                break;
            case "diesel":
                impuestoBase *= 1.2; // 20% de recargo
                break;
        }

        return Math.max(impuestoBase, 100.0); // Impuesto mínimo de 100
    }
}