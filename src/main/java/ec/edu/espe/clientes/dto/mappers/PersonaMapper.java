package ec.edu.espe.clientes.dto.mappers;


import ec.edu.espe.clientes.dto.requests.PersonaJuridicaRequestDto;
import ec.edu.espe.clientes.dto.requests.PersonaNaturalRequestDto;
import ec.edu.espe.clientes.dto.responses.PersonaResponseDto;
import ec.edu.espe.clientes.model.Persona;
import ec.edu.espe.clientes.model.PersonaJuridica;
import ec.edu.espe.clientes.model.PersonaNatural;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper para conversión entre Entidades Persona y DTOs
 * Usa MapStruct para generar código de mapeo automáticamente
 * @Mapper(componentModel = "spring"): Crea un bean de Spring
 */
@Mapper(componentModel = "spring") // ¡IMPORTANTE! Esto crea el bean de Spring
public interface PersonaMapper {

    /**
     * Convierte PersonaNaturalRequestDto a entidad PersonaNatural
     * @Mapping(target = "id", ignore = true): Ignora el ID (se genera automáticamente)
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "activo", ignore = true)
    PersonaNatural toEntity(PersonaNaturalRequestDto dto);

    /**
     * Convierte PersonaJuridicaRequestDto a entidad PersonaJuridica
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "activo", ignore = true)
    PersonaJuridica toEntity(PersonaJuridicaRequestDto dto);

    /**
     * Convierte entidad Persona a PersonaResponseDto
     * @Mapping target = "tipoPersona": Usa el método getTipoPersona()
     */
    @Mapping(target = "tipoPersona", expression = "java(getTipoPersona(persona))")
    PersonaResponseDto toDto(Persona persona);

    /**
     * Determina el tipo de persona basado en la clase concreta
     * Este método será implementado por MapStruct
     */
    default String getTipoPersona(Persona persona) {
        if (persona == null) {
            return "DESCONOCIDO";
        }
        if (persona instanceof PersonaNatural) {
            return "NATURAL";
        } else if (persona instanceof PersonaJuridica) {
            return "JURIDICA";
        }
        return "DESCONOCIDO";
    }
}
