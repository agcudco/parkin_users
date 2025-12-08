package ec.edu.espe.clientes.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Clase que representa una Moto
 * Hereda de Vehiculo e implementa cálculo de impuesto específico
 */
@Entity
@Table(name = "motos")
@DiscriminatorValue("MOTO")
@PrimaryKeyJoinColumn(name = "vehiculo_id")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Moto extends Vehiculo {

    @Column(name = "cilindrada", nullable = false)
    private Integer cilindrada; // Cilindrada en cc (centímetros cúbicos)

    @Column(name = "tipo_moto", length = 20)
    private String tipoMoto; // Tipo: deportiva, cruiser, sport, etc.

    @Column(name = "tiene_casco")
    private Boolean tieneCasco; // Si incluye casco en el registro

    /**
     * Implementación concreta del cálculo de impuesto para motos
     * Fórmula: cilindrada * 0.15 (ejemplo)
     * @return double valor del impuesto
     */
    @Override
    public double calcularImpuesto() {
        double impuestoBase = this.cilindrada * 0.15;

        // Recargo por año antiguo
        int antiguedad = java.time.Year.now().getValue() - this.getAnioFabricacion();
        if (antiguedad > 10) {
            impuestoBase *= 1.10; // 10% de recargo
        }

        return Math.max(impuestoBase, 50.0); // Impuesto mínimo de 50
    }
}