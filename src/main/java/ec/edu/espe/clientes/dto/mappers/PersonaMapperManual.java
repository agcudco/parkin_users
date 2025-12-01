package ec.edu.espe.clientes.dto.mappers;


import ec.edu.espe.clientes.dto.requests.PersonaJuridicaRequestDto;
import ec.edu.espe.clientes.dto.requests.PersonaNaturalRequestDto;
import ec.edu.espe.clientes.dto.responses.PersonaResponseDto;
import ec.edu.espe.clientes.model.Persona;
import ec.edu.espe.clientes.model.PersonaJuridica;
import ec.edu.espe.clientes.model.PersonaNatural;
import org.springframework.stereotype.Component;

/**
 * Mapper manual para Persona (alternativa a MapStruct)
 */
@Component // Anotación para que Spring lo detecte como bean
public class PersonaMapperManual {

    /**
     * Convierte PersonaNaturalRequestDto a entidad PersonaNatural
     */
    public PersonaNatural toEntity(PersonaNaturalRequestDto dto) {
        if (dto == null) {
            return null;
        }

        return PersonaNatural.builder()
                .identificacion(dto.getIdentificacion())
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .apellido(dto.getApellido())
                .fechaNacimiento(dto.getFechaNacimiento())
                .genero(dto.getGenero())
                .build();
    }

    /**
     * Convierte PersonaJuridicaRequestDto a entidad PersonaJuridica
     */
    public PersonaJuridica toEntity(PersonaJuridicaRequestDto dto) {
        if (dto == null) {
            return null;
        }

        return PersonaJuridica.builder()
                .identificacion(dto.getIdentificacion())
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .nombreComercial(dto.getNombreComercial())
                .razonSocial(dto.getRazonSocial())
                .representanteLegal(dto.getRepresentanteLegal())
                .actividadEconomica(dto.getActividadEconomica())
                .build();
    }

    /**
     * Convierte entidad Persona a PersonaResponseDto
     */
    public PersonaResponseDto toDto(Persona persona) {
        if (persona == null) {
            return null;
        }

        String tipoPersona = determinarTipoPersona(persona);

        return PersonaResponseDto.builder()
                .id(persona.getId())
                .identificacion(persona.getIdentificacion())
                .nombre(persona.getNombre())
                .email(persona.getEmail())
                .telefono(persona.getTelefono())
                .tipoPersona(tipoPersona)
                .fechaCreacion(persona.getFechaCreacion())
                .activo(persona.getActivo())
                .build();
    }

    /**
     * Determina el tipo de persona basado en la clase concreta
     */
    private String determinarTipoPersona(Persona persona) {
        if (persona instanceof PersonaNatural) {
            return "NATURAL";
        } else if (persona instanceof PersonaJuridica) {
            return "JURIDICA";
        }
        return "DESCONOCIDO";
    }
}