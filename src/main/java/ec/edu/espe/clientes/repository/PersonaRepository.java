package ec.edu.espe.clientes.repository;


import ec.edu.espe.clientes.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para entidades Persona
 * Patrón: Repository para abstraer el acceso a datos
 */
@Repository
public interface PersonaRepository extends JpaRepository<Persona, UUID> {

    // Buscar por identificación (única)
    Optional<Persona> findByIdentificacion(String identificacion);

    // Buscar por email (único)
    Optional<Persona> findByEmail(String email);

    // Verificar existencia por identificación
    boolean existsByIdentificacion(String identificacion);

    // Verificar existencia por email
    boolean existsByEmail(String email);

    // Buscar personas activas
    List<Persona> findByActivoTrue();

    // Buscar por tipo de persona usando consulta nativa
    @Query(value = "SELECT p.* FROM personas p WHERE p.id IN " +
            "(SELECT pn.id FROM personas_naturales pn) AND p.activo = true",
            nativeQuery = true)
    List<Persona> findPersonasNaturalesActivas();

    @Query(value = "SELECT p.* FROM personas p WHERE p.id IN " +
            "(SELECT pj.id FROM personas_juridicas pj) AND p.activo = true",
            nativeQuery = true)
    List<Persona> findPersonasJuridicasActivas();

    // Buscar por nombre (case insensitive)
    @Query("SELECT p FROM Persona p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND p.activo = true")
    List<Persona> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);
}