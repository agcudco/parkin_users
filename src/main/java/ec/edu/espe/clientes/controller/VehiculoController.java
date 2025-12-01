package ec.edu.espe.clientes.controller;

import ec.edu.espe.clientes.dto.requests.AutoFamiliarRequestDto;
import ec.edu.espe.clientes.dto.requests.MotoRequestDto;
import ec.edu.espe.clientes.dto.requests.VehiculoResponseDto;
import ec.edu.espe.clientes.services.VehiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para gestión de Vehículos
 * Principio SOLID: Single Responsibility Principle (SRP)
 */
@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoService vehiculoService;

    @PostMapping("/moto")
    public ResponseEntity<VehiculoResponseDto> createMoto(
            @Valid @RequestBody MotoRequestDto request) {
        VehiculoResponseDto response = vehiculoService.createMoto(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/auto-familiar")
    public ResponseEntity<VehiculoResponseDto> createAutoFamiliar(
            @Valid @RequestBody AutoFamiliarRequestDto request) {
        VehiculoResponseDto response = vehiculoService.createAutoFamiliar(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VehiculoResponseDto>> getAllVehiculos() {
        List<VehiculoResponseDto> response = vehiculoService.getAllVehiculos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponseDto> getVehiculoById(@PathVariable UUID id) {
        VehiculoResponseDto response = vehiculoService.getVehiculoById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<VehiculoResponseDto> getVehiculoByPlaca(@PathVariable String placa) {
        VehiculoResponseDto response = vehiculoService.getVehiculoByPlaca(placa);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/motos")
    public ResponseEntity<List<VehiculoResponseDto>> getMotos() {
        List<VehiculoResponseDto> response = vehiculoService.getMotos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/autos-familiares")
    public ResponseEntity<List<VehiculoResponseDto>> getAutosFamiliares() {
        List<VehiculoResponseDto> response = vehiculoService.getAutosFamiliares();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/propietario/{propietarioId}")
    public ResponseEntity<List<VehiculoResponseDto>> getVehiculosByPropietario(
            @PathVariable UUID propietarioId) {
        List<VehiculoResponseDto> response = vehiculoService.getVehiculosByPropietario(propietarioId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/moto/{id}")
    public ResponseEntity<VehiculoResponseDto> updateMoto(
            @PathVariable UUID id,
            @Valid @RequestBody MotoRequestDto request) {
        VehiculoResponseDto response = vehiculoService.updateMoto(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/auto-familiar/{id}")
    public ResponseEntity<VehiculoResponseDto> updateAutoFamiliar(
            @PathVariable UUID id,
            @Valid @RequestBody AutoFamiliarRequestDto request) {
        VehiculoResponseDto response = vehiculoService.updateAutoFamiliar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehiculo(@PathVariable UUID id) {
        vehiculoService.deleteVehiculo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/calcular-impuesto")
    public ResponseEntity<Double> calcularImpuesto(@PathVariable UUID id) {
        Double impuesto = vehiculoService.calcularImpuestoVehiculo(id);
        return ResponseEntity.ok(impuesto);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<VehiculoResponseDto>> searchByMarca(
            @RequestParam String marca) {
        List<VehiculoResponseDto> response = vehiculoService.searchByMarca(marca);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/propietario/{propietarioId}/existe")
    public ResponseEntity<Boolean> existsPropietario(@PathVariable UUID propietarioId) {
        boolean exists = vehiculoService.existsPropietario(propietarioId);
        return ResponseEntity.ok(exists);
    }
}