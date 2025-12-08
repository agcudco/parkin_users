package ec.edu.espe.clientes.repository;

import ec.edu.espe.clientes.model.AutoFamiliar;
import ec.edu.espe.clientes.model.Moto;
import ec.edu.espe.clientes.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, UUID> {

    // Buscar por placa
    Optional<Vehiculo> findByPlaca(String placa);

    // Verificar existencia
    boolean existsByPlaca(String placa);

    // Buscar vehículos activos
    List<Vehiculo> findByActivoTrue();

    // Buscar vehículos por propietario
    List<Vehiculo> findByPropietarioIdAndActivoTrue(UUID propietarioId);

    // Buscar motos activas (JPQL seguro)
    @Query("SELECT m FROM Moto m WHERE m.activo = true")
    List<Moto> findMotosActivas();

    // Buscar autos familiares activos (JPQL seguro)
    @Query("SELECT af FROM AutoFamiliar af WHERE af.activo = true")
    List<AutoFamiliar> findAutosFamiliaresActivos();

    // Contar vehículos por propietario
    long countByPropietarioIdAndActivoTrue(UUID propietarioId);

    // Buscar por marca
    List<Vehiculo> findByMarcaContainingIgnoreCaseAndActivoTrue(String marca);
}
