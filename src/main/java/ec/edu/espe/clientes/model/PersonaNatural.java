package ec.edu.espe.clientes.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * Clase que representa una Persona Natural (física)
 * Hereda de Persona e implementa validación específica
 */
@Entity
@Table(name = "personas_naturales")
@DiscriminatorValue("NATURAL")
@PrimaryKeyJoinColumn(name = "persona_id")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true) // Lombok: incluye campos de la superclase en equals/hashCode
@SuperBuilder
public class PersonaNatural extends Persona {

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido; // Apellido de la persona natural

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento; // Fecha de nacimiento

    @Column(name = "genero", length = 1)
    private String genero; // Género (M, F, O)

    /**
     * Implementación concreta de validación para persona natural
     * Valida cédula de ciudadanía (10 dígitos)
     * @return boolean true si la identificación es válida
     */
    @Override
    public boolean validarIdentificacion() {
        if (this.getIdentificacion() == null) return false;

        String cedula = this.getIdentificacion().trim();

        // Validar que sea numérica y tenga 10 dígitos
        if (!cedula.matches("\\d{10}")) {
            return false;
        }

        // Validar provincia (primeros dos dígitos entre 01-24)
        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (provincia < 1 || provincia > 24) {
            return false;
        }

        // Algoritmo de validación de cédula ecuatoriana
        int total = 0;
        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};

        for (int i = 0; i < 9; i++) {
            int valor = Integer.parseInt(cedula.substring(i, i + 1)) * coeficientes[i];
            if (valor >= 10) {
                valor -= 9;
            }
            total += valor;
        }

        int digitoVerificador = Integer.parseInt(cedula.substring(9, 10));
        int resultado = (total % 10 == 0) ? 0 : 10 - (total % 10);

        return resultado == digitoVerificador;
    }
}
