package ec.edu.espe.clientes.dto.requests;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

/**
 * DTO para recibir datos de Persona Natural desde la API
 * Hereda validaciones de PersonaRequestDto y agrega específicas
 */
@Data
public class PersonaNaturalRequestDto extends PersonaRequestDto {

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    private String apellido; // Apellido de la persona natural

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaNacimiento; // Fecha de nacimiento

    @Pattern(regexp = "[MFO]", message = "El género debe ser M, F u O")
    private String genero; // Género (M, F, O)
}