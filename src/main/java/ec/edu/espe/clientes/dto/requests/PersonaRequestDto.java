package ec.edu.espe.clientes.dto.requests;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * DTO para recibir datos de Persona desde la API
 * Incluye validaciones con Bean Validation
 */
@Data
public class PersonaRequestDto {

    @NotBlank(message = "La identificación es obligatoria")
    @Size(min = 10, max = 13, message = "La identificación debe tener entre 10 y 13 caracteres")
    @Pattern(regexp = "\\d+", message = "La identificación debe contener solo números")
    private String identificacion; // Número de identificación

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre; // Nombre completo o razón social

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email; // Correo electrónico

    @Size(min = 7, max = 15, message = "El teléfono debe tener entre 7 y 15 caracteres")
    @Pattern(regexp = "[0-9+\\- ]+", message = "El teléfono contiene caracteres inválidos")
    private String telefono; // Número de teléfono
}