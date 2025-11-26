package ec.edu.espe.clientes.dto.requests;


import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.UUID;

/**
 * DTO para recibir datos de Vehículo desde la API
 */
@Data
public class VehiculoRequestDto {

    @NotBlank(message = "La placa es obligatoria")
    @Size(min = 6, max = 10, message = "La placa debe tener entre 6 y 10 caracteres")
    @Pattern(regexp = "[A-Z0-9-]+", message = "La placa solo puede contener letras mayúsculas, números y guiones")
    private String placa; // Número de placa del vehículo

    @NotBlank(message = "La marca es obligatoria")
    @Size(min = 2, max = 50, message = "La marca debe tener entre 2 y 50 caracteres")
    private String marca; // Marca del vehículo

    @NotBlank(message = "El modelo es obligatorio")
    @Size(min = 1, max = 50, message = "El modelo debe tener entre 1 y 50 caracteres")
    private String modelo; // Modelo del vehículo

    @Size(max = 30, message = "El color no puede exceder 30 caracteres")
    private String color; // Color del vehículo

    @NotNull(message = "El año de fabricación es obligatorio")
    @Min(value = 1900, message = "El año de fabricación debe ser mayor a 1900")
    @Max(value = 2030, message = "El año de fabricación no puede ser mayor a 2030")
    private Integer anioFabricacion; // Año de fabricación

    @NotNull(message = "El ID del propietario es obligatorio")
    private UUID propietarioId; // ID del dueño del vehículo
}