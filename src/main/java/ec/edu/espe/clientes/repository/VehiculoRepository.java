package ec.edu.espe.clientes.repository;

import ec.edu.espe.clientes.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para entidades Vehiculo
 */
@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, UUID> {

    // Buscar por placa (única)
    Optional<Vehiculo> findByPlaca(String placa);

    // Verificar existencia por placa
    boolean existsByPlaca(String placa);

    // Buscar vehículos activos
    List<Vehiculo> findByActivoTrue();

    // Buscar vehículos por propietario
    List<Vehiculo> findByPropietarioIdAndActivoTrue(UUID propietarioId);

    // Buscar motos activas
    @Query(value = "SELECT v.* FROM vehiculos v WHERE v.id IN " +
            "(SELECT m.id FROM motos m) AND v.activo = true",
            nativeQuery = true)
    List<Vehiculo> findMotosActivas();

    // Buscar autos familiares activos
    @Query(value = "SELECT v.* FROM vehiculos v WHERE v.id IN " +
            "(SELECT af.id FROM autos_familiares af) AND v.activo = true",
            nativeQuery = true)
    List<Vehiculo> findAutosFamiliaresActivos();

    // Contar vehículos por propietario
    long countByPropietarioIdAndActivoTrue(UUID propietarioId);

    // Buscar por marca
    List<Vehiculo> findByMarcaContainingIgnoreCaseAndActivoTrue(String marca);

    List<Vehiculo> findAutosFamiliaresActivas();
}
