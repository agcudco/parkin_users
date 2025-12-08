package ec.edu.espe.clientes.services.impl;

//import jakarta.transaction.Transactional;

import ec.edu.espe.clientes.dto.mappers.VehiculoMapper;
import ec.edu.espe.clientes.dto.mappers.VehiculoMapperManual;
import ec.edu.espe.clientes.dto.requests.AutoFamiliarRequestDto;
import ec.edu.espe.clientes.dto.requests.MotoRequestDto;
import ec.edu.espe.clientes.dto.requests.VehiculoResponseDto;
import ec.edu.espe.clientes.model.AutoFamiliar;
import ec.edu.espe.clientes.model.Moto;
import ec.edu.espe.clientes.model.Persona;
import ec.edu.espe.clientes.model.Vehiculo;
import ec.edu.espe.clientes.repository.PersonaRepository;
import ec.edu.espe.clientes.repository.VehiculoRepository;
import ec.edu.espe.clientes.services.VehiculoService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de Vehículos
 * Principio SOLID: Single Responsibility Principle (SRP)
 */
@Service
@RequiredArgsConstructor
@Transactional
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final PersonaRepository personaRepository;
    private final VehiculoMapperManual vehiculoMapper;

    @Override
    @Transactional
    public VehiculoResponseDto createMoto(MotoRequestDto request) {
        // Validar placa única
        if (vehiculoRepository.existsByPlaca(request.getPlaca())) {
            throw new RuntimeException("Ya existe un vehículo con esta placa: " + request.getPlaca());
        }

        // Validar que el propietario existe y está activo
        Persona propietario = personaRepository.findById(request.getPropietarioId())
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado con ID: " + request.getPropietarioId()));

        if (!propietario.getActivo()) {
            throw new RuntimeException("El propietario está inactivo");
        }

        // Convertir DTO a entidad
        Moto moto = vehiculoMapper.toEntity(request);
        moto.setPropietario(propietario);

        // Persistir en base de datos
        Vehiculo vehiculoGuardado = vehiculoRepository.save(moto);

        // Convertir a DTO de respuesta
        return vehiculoMapper.toDto(vehiculoGuardado);
    }

    @Override
    @Transactional
    public VehiculoResponseDto createAutoFamiliar(AutoFamiliarRequestDto request) {
        // Validar placa única
        if (vehiculoRepository.existsByPlaca(request.getPlaca())) {
            throw new RuntimeException("Ya existe un vehículo con esta placa: " + request.getPlaca());
        }

        // Validar que el propietario existe y está activo
        Persona propietario = personaRepository.findById(request.getPropietarioId())
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado con ID: " + request.getPropietarioId()));

        if (!propietario.getActivo()) {
            throw new RuntimeException("El propietario está inactivo");
        }

        // Convertir DTO a entidad
        AutoFamiliar autoFamiliar = vehiculoMapper.toEntity(request);
        autoFamiliar.setPropietario(propietario);

        // Persistir en base de datos
        Vehiculo vehiculoGuardado = vehiculoRepository.save(autoFamiliar);

        // Convertir a DTO de respuesta
        return vehiculoMapper.toDto(vehiculoGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoResponseDto> getAllVehiculos() {
        return vehiculoRepository.findByActivoTrue().stream()
                .map(vehiculoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculoResponseDto getVehiculoById(UUID id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + id));

        if (!vehiculo.getActivo()) {
            throw new RuntimeException("El vehículo está inactivo");
        }

        return vehiculoMapper.toDto(vehiculo);
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculoResponseDto getVehiculoByPlaca(String placa) {
        Vehiculo vehiculo = vehiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con placa: " + placa));

        if (!vehiculo.getActivo()) {
            throw new RuntimeException("El vehículo está inactivo");
        }

        return vehiculoMapper.toDto(vehiculo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoResponseDto> getMotos() {
        return vehiculoRepository.findMotosActivas().stream()
                .map(vehiculoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoResponseDto> getAutosFamiliares() {
        return vehiculoRepository.findAutosFamiliaresActivos().stream()
                .map(vehiculoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoResponseDto> getVehiculosByPropietario(UUID propietarioId) {
        // Validar que el propietario existe
        if (!personaRepository.existsById(propietarioId)) {
            throw new RuntimeException("Propietario no encontrado con ID: " + propietarioId);
        }

        return vehiculoRepository.findByPropietarioIdAndActivoTrue(propietarioId).stream()
                .map(vehiculoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VehiculoResponseDto updateMoto(UUID id, MotoRequestDto request) {
        Moto motoExistente = (Moto) vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto no encontrada con ID: " + id));

        if (!motoExistente.getActivo()) {
            throw new RuntimeException("No se puede actualizar una moto inactiva");
        }

        // Validar cambios en placa
        if (!motoExistente.getPlaca().equals(request.getPlaca()) &&
                vehiculoRepository.existsByPlaca(request.getPlaca())) {
            throw new RuntimeException("Ya existe otro vehículo con esta placa: " + request.getPlaca());
        }

        // Validar cambios en propietario
        Persona propietario = motoExistente.getPropietario();
        if (!propietario.getId().equals(request.getPropietarioId())) {
            propietario = personaRepository.findById(request.getPropietarioId())
                    .orElseThrow(() -> new RuntimeException("Propietario no encontrado con ID: " + request.getPropietarioId()));

            if (!propietario.getActivo()) {
                throw new RuntimeException("El nuevo propietario está inactivo");
            }
        }

        // Actualizar campos básicos de vehículo
        motoExistente.setPlaca(request.getPlaca());
        motoExistente.setMarca(request.getMarca());
        motoExistente.setModelo(request.getModelo());
        motoExistente.setColor(request.getColor());
        motoExistente.setAnioFabricacion(request.getAnioFabricacion());
        motoExistente.setPropietario(propietario);

        // Actualizar campos específicos de moto
        motoExistente.setCilindrada(request.getCilindrada());
        motoExistente.setTipoMoto(request.getTipoMoto());
        motoExistente.setTieneCasco(request.getTieneCasco());

        Vehiculo vehiculoActualizado = vehiculoRepository.save(motoExistente);
        return vehiculoMapper.toDto(vehiculoActualizado);
    }

    @Override
    @Transactional
    public VehiculoResponseDto updateAutoFamiliar(UUID id, AutoFamiliarRequestDto request) {
        AutoFamiliar autoExistente = (AutoFamiliar) vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auto familiar no encontrado con ID: " + id));

        if (!autoExistente.getActivo()) {
            throw new RuntimeException("No se puede actualizar un auto familiar inactivo");
        }

        // Validar cambios en placa
        if (!autoExistente.getPlaca().equals(request.getPlaca()) &&
                vehiculoRepository.existsByPlaca(request.getPlaca())) {
            throw new RuntimeException("Ya existe otro vehículo con esta placa: " + request.getPlaca());
        }

        // Validar cambios en propietario
        Persona propietario = autoExistente.getPropietario();
        if (!propietario.getId().equals(request.getPropietarioId())) {
            propietario = personaRepository.findById(request.getPropietarioId())
                    .orElseThrow(() -> new RuntimeException("Propietario no encontrado con ID: " + request.getPropietarioId()));

            if (!propietario.getActivo()) {
                throw new RuntimeException("El nuevo propietario está inactivo");
            }
        }

        // Actualizar campos básicos de vehículo
        autoExistente.setPlaca(request.getPlaca());
        autoExistente.setMarca(request.getMarca());
        autoExistente.setModelo(request.getModelo());
        autoExistente.setColor(request.getColor());
        autoExistente.setAnioFabricacion(request.getAnioFabricacion());
        autoExistente.setPropietario(propietario);

        // Actualizar campos específicos de auto familiar
        autoExistente.setNumeroPuertas(request.getNumeroPuertas());
        autoExistente.setCapacidadMaletero(request.getCapacidadMaletero());
        autoExistente.setTieneAireAcondicionado(request.getTieneAireAcondicionado());
        autoExistente.setTipoCombustible(request.getTipoCombustible());

        Vehiculo vehiculoActualizado = vehiculoRepository.save(autoExistente);
        return vehiculoMapper.toDto(vehiculoActualizado);
    }

    @Override
    @Transactional
    public void deleteVehiculo(UUID id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + id));

        // Soft delete
        vehiculo.setActivo(false);
        vehiculoRepository.save(vehiculo);
    }

    @Override
    @Transactional(readOnly = true)
    public Double calcularImpuestoVehiculo(UUID id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + id));

        if (!vehiculo.getActivo()) {
            throw new RuntimeException("No se puede calcular impuesto de un vehículo inactivo");
        }

        return vehiculo.calcularImpuesto();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoResponseDto> searchByMarca(String marca) {
        return vehiculoRepository.findByMarcaContainingIgnoreCaseAndActivoTrue(marca).stream()
                .map(vehiculoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsPropietario(UUID propietarioId) {
        return personaRepository.existsById(propietarioId);
    }
}
