package ec.edu.espe.clientes.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Clase que representa una Persona Jurídica (empresa)
 * Hereda de Persona e implementa validación específica
 */
@Entity
@Table(name = "personas_juridicas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class PersonaJuridica extends Persona {

    @Column(name = "nombre_comercial", nullable = false, length = 200)
    private String nombreComercial; // Nombre comercial de la empresa

    @Column(name = "razon_social", nullable = false, length = 200)
    private String razonSocial; // Razón social legal

    @Column(name = "representante_legal", nullable = false, length = 100)
    private String representanteLegal; // Nombre del representante legal

    @Column(name = "actividad_economica", length = 100)
    private String actividadEconomica; // Giro o actividad económica

    /**
     * Implementación concreta de validación para persona jurídica
     * Valida RUC (13 dígitos)
     * @return boolean true si la identificación es válida
     */
    @Override
    public boolean validarIdentificacion() {
        if (this.getIdentificacion() == null) return false;

        String ruc = this.getIdentificacion().trim();

        // Validar que sea numérica y tenga 13 dígitos
        if (!ruc.matches("\\d{13}")) {
            return false;
        }

        // Validar que los primeros dos dígitos sean provincia válida
        int provincia = Integer.parseInt(ruc.substring(0, 2));
        if (provincia < 1 || provincia > 24) {
            return false;
        }

        // Validar que el tercer dígito sea 9 (para personas jurídicas)
        int tercerDigito = Integer.parseInt(ruc.substring(2, 3));
        if (tercerDigito != 9) {
            return false;
        }

        // Algoritmo de validación de RUC
        int total = 0;
        int[] coeficientes = {4, 3, 2, 7, 6, 5, 4, 3, 2};

        for (int i = 0; i < 9; i++) {
            int valor = Integer.parseInt(ruc.substring(i, i + 1)) * coeficientes[i];
            total += valor;
        }

        int digitoVerificador = Integer.parseInt(ruc.substring(9, 10));
        int resultado = (total % 11 == 0) ? 0 : 11 - (total % 11);

        return resultado == digitoVerificador;
    }
}
