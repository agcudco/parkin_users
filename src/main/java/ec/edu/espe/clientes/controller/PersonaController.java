package ec.edu.espe.clientes.controller;

import ec.edu.espe.clientes.dto.requests.PersonaJuridicaRequestDto;
import ec.edu.espe.clientes.dto.requests.PersonaNaturalRequestDto;
import ec.edu.espe.clientes.dto.responses.PersonaResponseDto;
import ec.edu.espe.clientes.services.PersonaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para gestión de Personas
 * Expone endpoints para operaciones CRUD y búsquedas
 */
@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaService personaService;

    @PostMapping("/natural")
    public ResponseEntity<PersonaResponseDto> createPersonaNatural(
            @Valid @RequestBody PersonaNaturalRequestDto request) {
        PersonaResponseDto response = personaService.createPersonaNatural(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/juridica")
    public ResponseEntity<PersonaResponseDto> createPersonaJuridica(
            @Valid @RequestBody PersonaJuridicaRequestDto request) {
        PersonaResponseDto response = personaService.createPersonaJuridica(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PersonaResponseDto>> getAllPersonas() {
        List<PersonaResponseDto> response = personaService.getAllPersonas();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonaResponseDto> getPersonaById(@PathVariable UUID id) {
        PersonaResponseDto response = personaService.getPersonaById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/identificacion/{identificacion}")
    public ResponseEntity<PersonaResponseDto> getPersonaByIdentificacion(
            @PathVariable String identificacion) {
        PersonaResponseDto response = personaService.getPersonaByIdentificacion(identificacion);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/naturales")
    public ResponseEntity<List<PersonaResponseDto>> getPersonasNaturales() {
        List<PersonaResponseDto> response = personaService.getPersonasNaturales();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/juridicas")
    public ResponseEntity<List<PersonaResponseDto>> getPersonasJuridicas() {
        List<PersonaResponseDto> response = personaService.getPersonasJuridicas();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/natural/{id}")
    public ResponseEntity<PersonaResponseDto> updatePersonaNatural(
            @PathVariable UUID id,
            @Valid @RequestBody PersonaNaturalRequestDto request) {
        PersonaResponseDto response = personaService.updatePersonaNatural(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/juridica/{id}")
    public ResponseEntity<PersonaResponseDto> updatePersonaJuridica(
            @PathVariable UUID id,
            @Valid @RequestBody PersonaJuridicaRequestDto request) {
        PersonaResponseDto response = personaService.updatePersonaJuridica(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable UUID id) {
        personaService.deletePersona(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/validar-identificacion")
    public ResponseEntity<Boolean> validarIdentificacion(@PathVariable UUID id) {
        boolean isValid = personaService.validarIdentificacionPersona(id);
        return ResponseEntity.ok(isValid);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<PersonaResponseDto>> searchByNombre(
            @RequestParam String nombre) {
        List<PersonaResponseDto> response = personaService.searchByNombre(nombre);
        return ResponseEntity.ok(response);
    }
}