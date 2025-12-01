package ec.edu.espe.clientes.dto.mappers;


import ec.edu.espe.clientes.dto.requests.AutoFamiliarRequestDto;
import ec.edu.espe.clientes.dto.requests.MotoRequestDto;
import ec.edu.espe.clientes.dto.requests.VehiculoResponseDto;
import ec.edu.espe.clientes.model.AutoFamiliar;
import ec.edu.espe.clientes.model.Moto;
import ec.edu.espe.clientes.model.Vehiculo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper para conversión entre Entidades Vehiculo y DTOs
 */
@Mapper(componentModel = "spring") // ¡IMPORTANTE! Esto crea el bean de Spring
public interface VehiculoMapper {

    /**
     * Convierte MotoRequestDto a entidad Moto
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "propietario", ignore = true) // Se establece manualmente
    Moto toEntity(MotoRequestDto dto);

    /**
     * Convierte AutoFamiliarRequestDto a entidad AutoFamiliar
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "propietario", ignore = true) // Se establece manualmente
    AutoFamiliar toEntity(AutoFamiliarRequestDto dto);

    /**
     * Convierte entidad Vehiculo a VehiculoResponseDto
     * expression: Permite usar métodos Java personalizados
     */
    @Mapping(target = "tipoVehiculo", expression = "java(getTipoVehiculo(vehiculo))")
    @Mapping(target = "propietarioId", source = "propietario.id")
    @Mapping(target = "propietarioNombre", source = "propietario.nombre")
    @Mapping(target = "impuesto", expression = "java(vehiculo.calcularImpuesto())")
    VehiculoResponseDto toDto(Vehiculo vehiculo);

    /**
     * Determina el tipo de vehículo basado en la clase concreta
     */
    default String getTipoVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            return "DESCONOCIDO";
        }
        if (vehiculo instanceof Moto) {
            return "MOTO";
        } else if (vehiculo instanceof AutoFamiliar) {
            return "AUTO_FAMILIAR";
        }
        return "DESCONOCIDO";
    }
}