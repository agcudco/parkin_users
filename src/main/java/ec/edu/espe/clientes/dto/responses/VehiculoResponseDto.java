package ec.edu.espe.clientes.dto.responses;


import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para enviar datos de Vehículo a la API
 */
@Data
@Builder
public class VehiculoResponseDto {
    private UUID id; // Identificador único
    private String placa; // Número de placa
    private String marca; // Marca del vehículo
    private String modelo; // Modelo del vehículo
    private String color; // Color del vehículo
    private Integer anioFabricacion; // Año de fabricación
    private String tipoVehiculo; // Tipo: MOTO o AUTO_FAMILIAR
    private UUID propietarioId; // ID del propietario
    private String propietarioNombre; // Nombre del propietario
    private LocalDateTime fechaRegistro; // Fecha de registro
    private Double impuesto; // Impuesto calculado
    private Boolean activo; // Estado activo/inactivo
}
