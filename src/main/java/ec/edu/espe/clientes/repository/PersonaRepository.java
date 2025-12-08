package ec.edu.espe.clientes.repository;


import ec.edu.espe.clientes.model.Persona;
import ec.edu.espe.clientes.model.PersonaJuridica;
import ec.edu.espe.clientes.model.PersonaNatural;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface PersonaRepository extends JpaRepository<Persona, UUID> {

    // Buscar por identificación
    Optional<Persona> findByIdentificacion(String identificacion);

    // Buscar por email
    Optional<Persona> findByEmail(String email);

    // Verificar existencia
    boolean existsByIdentificacion(String identificacion);
    boolean existsByEmail(String email);

    // Buscar personas activas
    List<Persona> findByActivoTrue();

    // Buscar personas naturales activas (JPQL seguro)
    @Query("SELECT pn FROM PersonaNatural pn WHERE pn.activo = true")
    List<PersonaNatural> findPersonasNaturalesActivas();

    // Buscar personas jurídicas activas (JPQL seguro)
    @Query("SELECT pj FROM PersonaJuridica pj WHERE pj.activo = true")
    List<PersonaJuridica> findPersonasJuridicasActivas();

    // Buscar por nombre (sin consultas nativas)
    List<Persona> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);

    // Buscar por nombre (case insensitive)
    @Query("SELECT p FROM Persona p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND p.activo = true")
    List<Persona> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);
}