package ec.edu.espe.clientes.dto.mappers;

import ec.edu.espe.clientes.dto.requests.AutoFamiliarRequestDto;
import ec.edu.espe.clientes.dto.requests.MotoRequestDto;
import ec.edu.espe.clientes.dto.requests.VehiculoResponseDto;
import ec.edu.espe.clientes.model.AutoFamiliar;
import ec.edu.espe.clientes.model.Moto;
import ec.edu.espe.clientes.model.Persona;
import ec.edu.espe.clientes.model.Vehiculo;
import org.springframework.stereotype.Component;

/**
 * Mapper manual para Vehiculo (alternativa a MapStruct)
 */
@Component // Anotación para que Spring lo detecte como bean
public class VehiculoMapperManual {

    /**
     * Convierte MotoRequestDto a entidad Moto
     */
    public Moto toEntity(MotoRequestDto dto) {
        if (dto == null) {
            return null;
        }

        return Moto.builder()
                .placa(dto.getPlaca())
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .color(dto.getColor())
                .anioFabricacion(dto.getAnioFabricacion())
                .cilindrada(dto.getCilindrada())
                .tipoMoto(dto.getTipoMoto())
                .tieneCasco(dto.getTieneCasco())
                .build();
    }

    /**
     * Convierte AutoFamiliarRequestDto a entidad AutoFamiliar
     */
    public AutoFamiliar toEntity(AutoFamiliarRequestDto dto) {
        if (dto == null) {
            return null;
        }

        return AutoFamiliar.builder()
                .placa(dto.getPlaca())
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .color(dto.getColor())
                .anioFabricacion(dto.getAnioFabricacion())
                .numeroPuertas(dto.getNumeroPuertas())
                .capacidadMaletero(dto.getCapacidadMaletero())
                .tieneAireAcondicionado(dto.getTieneAireAcondicionado())
                .tipoCombustible(dto.getTipoCombustible())
                .build();
    }

    /**
     * Convierte entidad Vehiculo a VehiculoResponseDto
     */
    public VehiculoResponseDto toDto(Vehiculo vehiculo) {
        if (vehiculo == null) {
            return null;
        }

        String tipoVehiculo = determinarTipoVehiculo(vehiculo);
        Persona propietario = vehiculo.getPropietario();
        Double impuesto = vehiculo.calcularImpuesto();

        return VehiculoResponseDto.builder()
                .id(vehiculo.getId())
                .placa(vehiculo.getPlaca())
                .marca(vehiculo.getMarca())
                .modelo(vehiculo.getModelo())
                .color(vehiculo.getColor())
                .anioFabricacion(vehiculo.getAnioFabricacion())
                .tipoVehiculo(tipoVehiculo)
                .propietarioId(propietario != null ? propietario.getId() : null)
                .propietarioNombre(propietario != null ? propietario.getNombre() : null)
                .fechaRegistro(vehiculo.getFechaRegistro())
                .impuesto(impuesto != null ? impuesto : 0.0)
                .activo(vehiculo.getActivo())
                .build();
    }

    /**
     * Determina el tipo de vehículo basado en la clase concreta
     */
    private String determinarTipoVehiculo(Vehiculo vehiculo) {
        if (vehiculo instanceof Moto) {
            return "MOTO";
        } else if (vehiculo instanceof AutoFamiliar) {
            return "AUTO_FAMILIAR";
        }
        return "DESCONOCIDO";
    }
}