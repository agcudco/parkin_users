package ec.edu.espe.clientes.services;

import ec.edu.espe.clientes.dto.requests.PersonaJuridicaRequestDto;
import ec.edu.espe.clientes.dto.requests.PersonaNaturalRequestDto;
import ec.edu.espe.clientes.dto.responses.PersonaResponseDto;

import java.util.List;
import java.util.UUID;

/**
 * Interfaz de servicio para gestión de Personas
 * Patrón: Service Interface para inversión de dependencias (SOLID - DIP)
 */
public interface PersonaService {

    // Crear personas
    PersonaResponseDto createPersonaNatural(PersonaNaturalRequestDto request);
    PersonaResponseDto createPersonaJuridica(PersonaJuridicaRequestDto request);

    // Obtener personas
    List<PersonaResponseDto> getAllPersonas();
    PersonaResponseDto getPersonaById(UUID id);
    PersonaResponseDto getPersonaByIdentificacion(String identificacion);
    List<PersonaResponseDto> getPersonasNaturales();
    List<PersonaResponseDto> getPersonasJuridicas();

    // Actualizar personas
    PersonaResponseDto updatePersonaNatural(UUID id, PersonaNaturalRequestDto request);
    PersonaResponseDto updatePersonaJuridica(UUID id, PersonaJuridicaRequestDto request);

    // Eliminar persona (soft delete)
    void deletePersona(UUID id);

    // Validar identificación
    boolean validarIdentificacionPersona(UUID id);

    // Buscar por nombre
    List<PersonaResponseDto> searchByNombre(String nombre);
}