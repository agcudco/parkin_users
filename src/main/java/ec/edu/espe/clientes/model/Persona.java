package ec.edu.espe.clientes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase abstracta que representa una Persona en el sistema.
 * Implementa el patrón Strategy para diferentes tipos de personas.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Estrategia de herencia: tablas separadas
@Table(name = "personas")
@Data // Lombok: genera getters, setters, equals, hashCode, toString
@NoArgsConstructor // Lombok: constructor sin argumentos
@AllArgsConstructor // Lombok: constructor con todos los argumentos
public abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id; // Identificador único universal

    @Column(name = "identificacion", nullable = false, unique = true, length = 20)
    private String identificacion; // Número de identificación (cédula, RUC, etc.)

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre; // Nombre completo o razón social

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email; // Correo electrónico

    @Column(name = "telefono", length = 15)
    private String telefono; // Número de teléfono

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion; // Fecha de creación del registro

    @Column(name = "activo", nullable = false)
    private Boolean activo; // Estado activo/inactivo

    /**
     * Método abstracto para validar la identificación según el tipo de persona
     * Patrón: Template Method - cada subclase implementa su propia validación
     * @return boolean indicando si la identificación es válida
     */
    public abstract boolean validarIdentificacion();

    /**
     * Método hook que se ejecuta antes de persistir
     */
    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.activo = true;
    }
}
