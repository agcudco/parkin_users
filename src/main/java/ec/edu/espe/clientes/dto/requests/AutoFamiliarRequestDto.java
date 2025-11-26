package ec.edu.espe.clientes.dto.requests;


import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * DTO para recibir datos de Auto Familiar desde la API
 * Hereda validaciones de VehiculoRequestDto y agrega específicas
 */
@Data
public class AutoFamiliarRequestDto extends VehiculoRequestDto {

    @NotNull(message = "El número de puertas es obligatorio")
    @Min(value = 3, message = "El número mínimo de puertas es 3")
    @Max(value = 5, message = "El número máximo de puertas es 5")
    private Integer numeroPuertas; // Número de puertas

    @Min(value = 100, message = "La capacidad mínima del maletero es 100 litros")
    @Max(value = 1000, message = "La capacidad máxima del maletero es 1000 litros")
    private Integer capacidadMaletero; // Capacidad del maletero en litros

    private Boolean tieneAireAcondicionado; // Si tiene aire acondicionado

    @NotBlank(message = "El tipo de combustible es obligatorio")
    @Pattern(regexp = "(?i)(gasolina|diesel|eléctrico|híbrido)",
            message = "El tipo de combustible debe ser: gasolina, diesel, eléctrico o híbrido")
    private String tipoCombustible; // Tipo de combustible
}
