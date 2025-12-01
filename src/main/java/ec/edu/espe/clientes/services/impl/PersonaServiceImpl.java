package ec.edu.espe.clientes.services.impl;

import ec.edu.espe.clientes.dto.mappers.PersonaMapper;
import ec.edu.espe.clientes.dto.requests.PersonaJuridicaRequestDto;
import ec.edu.espe.clientes.dto.requests.PersonaNaturalRequestDto;
import ec.edu.espe.clientes.dto.responses.PersonaResponseDto;
import ec.edu.espe.clientes.model.Persona;
import ec.edu.espe.clientes.model.PersonaJuridica;
import ec.edu.espe.clientes.model.PersonaNatural;
import ec.edu.espe.clientes.repository.PersonaRepository;
import ec.edu.espe.clientes.services.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de Personas
 * Patrón: Service Implementation con transacciones
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository personaRepository;
    @Autowired
    private PersonaMapper personaMapper;

    @Override
    @Transactional
    public PersonaResponseDto createPersonaNatural(PersonaNaturalRequestDto request) {
        // Validar identificación única
        if (personaRepository.existsByIdentificacion(request.getIdentificacion())) {
            throw new RuntimeException("Ya existe una persona con esta identificación: " + request.getIdentificacion());
        }

        // Validar email único
        if (personaRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe una persona con este email: " + request.getEmail());
        }

        // Convertir DTO a entidad
        PersonaNatural personaNatural = personaMapper.toEntity(request);

        // Validar identificación según reglas de negocio
        if (!personaNatural.validarIdentificacion()) {
            throw new RuntimeException("La identificación no es válida para persona natural");
        }

        // Persistir en base de datos
        Persona personaGuardada = personaRepository.save(personaNatural);

        // Convertir a DTO de respuesta
        return personaMapper.toDto(personaGuardada);
    }

    @Override
    @Transactional
    public PersonaResponseDto createPersonaJuridica(PersonaJuridicaRequestDto request) {
        // Validar identificación única
        if (personaRepository.existsByIdentificacion(request.getIdentificacion())) {
            throw new RuntimeException("Ya existe una persona con esta identificación: " + request.getIdentificacion());
        }

        // Validar email único
        if (personaRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe una persona con este email: " + request.getEmail());
        }

        // Convertir DTO a entidad
        PersonaJuridica personaJuridica = personaMapper.toEntity(request);

        // Validar identificación según reglas de negocio
        if (!personaJuridica.validarIdentificacion()) {
            throw new RuntimeException("La identificación no es válida para persona jurídica");
        }

        // Persistir en base de datos
        Persona personaGuardada = personaRepository.save(personaJuridica);

        // Convertir a DTO de respuesta
        return personaMapper.toDto(personaGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaResponseDto> getAllPersonas() {
        return personaRepository.findByActivoTrue().stream()
                .map(personaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaResponseDto getPersonaById(UUID id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + id));

        if (!persona.getActivo()) {
            throw new RuntimeException("La persona está inactiva");
        }

        return personaMapper.toDto(persona);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaResponseDto getPersonaByIdentificacion(String identificacion) {
        Persona persona = personaRepository.findByIdentificacion(identificacion)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con identificación: " + identificacion));

        if (!persona.getActivo()) {
            throw new RuntimeException("La persona está inactiva");
        }

        return personaMapper.toDto(persona);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaResponseDto> getPersonasNaturales() {
        return personaRepository.findPersonasNaturalesActivas().stream()
                .map(personaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaResponseDto> getPersonasJuridicas() {
        return personaRepository.findPersonasJuridicasActivas().stream()
                .map(personaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PersonaResponseDto updatePersonaNatural(UUID id, PersonaNaturalRequestDto request) {
        PersonaNatural personaExistente = (PersonaNatural) personaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona natural no encontrada con ID: " + id));

        if (!personaExistente.getActivo()) {
            throw new RuntimeException("No se puede actualizar una persona inactiva");
        }

        // Validar cambios en identificación
        if (!personaExistente.getIdentificacion().equals(request.getIdentificacion()) &&
                personaRepository.existsByIdentificacion(request.getIdentificacion())) {
            throw new RuntimeException("Ya existe otra persona con esta identificación: " + request.getIdentificacion());
        }

        // Validar cambios en email
        if (!personaExistente.getEmail().equals(request.getEmail()) &&
                personaRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe otra persona con este email: " + request.getEmail());
        }

        // Actualizar campos
        personaExistente.setIdentificacion(request.getIdentificacion());
        personaExistente.setNombre(request.getNombre());
        personaExistente.setEmail(request.getEmail());
        personaExistente.setTelefono(request.getTelefono());
        personaExistente.setApellido(request.getApellido());
        personaExistente.setFechaNacimiento(request.getFechaNacimiento());
        personaExistente.setGenero(request.getGenero());

        // Validar identificación actualizada
        if (!personaExistente.validarIdentificacion()) {
            throw new RuntimeException("La nueva identificación no es válida para persona natural");
        }

        Persona personaActualizada = personaRepository.save(personaExistente);
        return personaMapper.toDto(personaActualizada);
    }

    @Override
    @Transactional
    public PersonaResponseDto updatePersonaJuridica(UUID id, PersonaJuridicaRequestDto request) {
        PersonaJuridica personaExistente = (PersonaJuridica) personaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona jurídica no encontrada con ID: " + id));

        if (!personaExistente.getActivo()) {
            throw new RuntimeException("No se puede actualizar una persona inactiva");
        }

        // Validar cambios en identificación
        if (!personaExistente.getIdentificacion().equals(request.getIdentificacion()) &&
                personaRepository.existsByIdentificacion(request.getIdentificacion())) {
            throw new RuntimeException("Ya existe otra persona con esta identificación: " + request.getIdentificacion());
        }

        // Validar cambios en email
        if (!personaExistente.getEmail().equals(request.getEmail()) &&
                personaRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe otra persona con este email: " + request.getEmail());
        }

        // Actualizar campos
        personaExistente.setIdentificacion(request.getIdentificacion());
        personaExistente.setNombre(request.getNombre());
        personaExistente.setEmail(request.getEmail());
        personaExistente.setTelefono(request.getTelefono());
        personaExistente.setNombreComercial(request.getNombreComercial());
        personaExistente.setRazonSocial(request.getRazonSocial());
        personaExistente.setRepresentanteLegal(request.getRepresentanteLegal());
        personaExistente.setActividadEconomica(request.getActividadEconomica());

        // Validar identificación actualizada
        if (!personaExistente.validarIdentificacion()) {
            throw new RuntimeException("La nueva identificación no es válida para persona jurídica");
        }

        Persona personaActualizada = personaRepository.save(personaExistente);
        return personaMapper.toDto(personaActualizada);
    }

    @Override
    @Transactional
    public void deletePersona(UUID id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + id));

        // Soft delete
        persona.setActivo(false);
        personaRepository.save(persona);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validarIdentificacionPersona(UUID id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + id));

        return persona.validarIdentificacion();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaResponseDto> searchByNombre(String nombre) {
        return personaRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(personaMapper::toDto)
                .collect(Collectors.toList());
    }
}
