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
@Mapper(componentModel = "spring")
public interface VehiculoMapper {

    VehiculoMapper INSTANCE = Mappers.getMapper(VehiculoMapper.class);

    /**
     * Convierte MotoRequestDto a entidad Moto
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "propietario", ignore = true)
    Moto toEntity(MotoRequestDto dto);

    /**
     * Convierte AutoFamiliarRequestDto a entidad AutoFamiliar
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "propietario", ignore = true)
    AutoFamiliar toEntity(AutoFamiliarRequestDto dto);

    /**
     * Convierte entidad Vehiculo a VehiculoResponseDto
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
        if (vehiculo instanceof Moto) {
            return "MOTO";
        } else if (vehiculo instanceof AutoFamiliar) {
            return "AUTO_FAMILIAR";
        }
        return "DESCONOCIDO";
    }
}