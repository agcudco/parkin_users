package ec.edu.espe.clientes.services;

import ec.edu.espe.clientes.dto.requests.AutoFamiliarRequestDto;
import ec.edu.espe.clientes.dto.requests.MotoRequestDto;
import ec.edu.espe.clientes.dto.requests.VehiculoResponseDto;

import java.util.List;
import java.util.UUID;

public interface VehiculoService {
    // Crear vehículos
    VehiculoResponseDto createMoto(MotoRequestDto request);

    VehiculoResponseDto createAutoFamiliar(AutoFamiliarRequestDto request);

    // Obtener vehículos
    List<VehiculoResponseDto> getAllVehiculos();

    VehiculoResponseDto getVehiculoById(UUID id);

    VehiculoResponseDto getVehiculoByPlaca(String placa);

    List<VehiculoResponseDto> getMotos();

    List<VehiculoResponseDto> getAutosFamiliares();

    List<VehiculoResponseDto> getVehiculosByPropietario(UUID propietarioId);

    // Actualizar vehículos
    VehiculoResponseDto updateMoto(UUID id, MotoRequestDto request);

    VehiculoResponseDto updateAutoFamiliar(UUID id, AutoFamiliarRequestDto request);

    // Eliminar vehículo (soft delete)
    void deleteVehiculo(UUID id);

    // Calcular impuesto
    Double calcularImpuestoVehiculo(UUID id);

    // Buscar por marca
    List<VehiculoResponseDto> searchByMarca(String marca);

    // Validar existencia de propietario
    boolean existsPropietario(UUID propietarioId);
}
