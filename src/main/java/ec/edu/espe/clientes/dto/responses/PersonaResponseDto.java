package ec.edu.espe.clientes.dto.responses;


import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para enviar datos de Persona a la API
 * Solo incluye datos necesarios para la respuesta
 */
@Data
@Builder
public class PersonaResponseDto {
    private UUID id; // Identificador único
    private String identificacion; // Número de identificación
    private String nombre; // Nombre completo o razón social
    private String email; // Correo electrónico
    private String telefono; // Número de teléfono
    private String tipoPersona; // Tipo: NATURAL o JURIDICA
    private LocalDateTime fechaCreacion; // Fecha de creación
    private Boolean activo; // Estado activo/inactivo
}