package ec.edu.espe.clientes.dto.requests;


import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * DTO para recibir datos de Persona Jurídica desde la API
 * Hereda validaciones de PersonaRequestDto y agrega específicas
 */
@Data
public class PersonaJuridicaRequestDto extends PersonaRequestDto {

    @NotBlank(message = "El nombre comercial es obligatorio")
    @Size(min = 2, max = 200, message = "El nombre comercial debe tener entre 2 y 200 caracteres")
    private String nombreComercial; // Nombre comercial de la empresa

    @NotBlank(message = "La razón social es obligatoria")
    @Size(min = 2, max = 200, message = "La razón social debe tener entre 2 y 200 caracteres")
    private String razonSocial; // Razón social legal

    @NotBlank(message = "El representante legal es obligatorio")
    @Size(min = 2, max = 100, message = "El representante legal debe tener entre 2 y 100 caracteres")
    private String representanteLegal; // Nombre del representante legal

    @Size(max = 100, message = "La actividad económica no puede exceder 100 caracteres")
    private String actividadEconomica; // Giro o actividad económica
}
