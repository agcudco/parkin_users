package ec.edu.espe.clientes.dto.requests;


import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * DTO para recibir datos de Moto desde la API
 * Hereda validaciones de VehiculoRequestDto y agrega específicas
 */
@Data
public class MotoRequestDto extends VehiculoRequestDto {

    @NotNull(message = "La cilindrada es obligatoria")
    @Min(value = 50, message = "La cilindrada mínima es 50cc")
    @Max(value = 2000, message = "La cilindrada máxima es 2000cc")
    private Integer cilindrada; // Cilindrada en cc

    @Size(max = 20, message = "El tipo de moto no puede exceder 20 caracteres")
    private String tipoMoto; // Tipo: deportiva, cruiser, sport, etc.

    private Boolean tieneCasco; // Si incluye casco en el registro
}
