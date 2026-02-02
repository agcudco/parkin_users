package ec.edu.espe.clientes.config;


import ec.edu.espe.clientes.model.AutoFamiliar;
import ec.edu.espe.clientes.model.Moto;
import ec.edu.espe.clientes.model.PersonaJuridica;
import ec.edu.espe.clientes.model.PersonaNatural;
import ec.edu.espe.clientes.repository.PersonaRepository;
import ec.edu.espe.clientes.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Configuración para inicializar datos de prueba en la base de datos.
 * Crea personas (naturales y jurídicas) y vehículos (motos y autos) para pruebas.
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    /**
     * Bean que inicializa datos de personas y vehículos al iniciar la aplicación.
     *
     * @param personaRepository Repositorio para operaciones CRUD de personas
     * @param vehiculoRepository Repositorio para operaciones CRUD de vehículos
     * @return CommandLineRunner que contiene la lógica de inicialización
     */
    @Bean
    @Transactional
    @Profile("!prod") // Solo se ejecuta en perfiles que NO sean producción
    public CommandLineRunner initData(
            PersonaRepository personaRepository,
            VehiculoRepository vehiculoRepository) {

        return args -> {
            log.info("🔄 Inicializando datos de prueba para Personas y Vehículos...");

            // Verificar si ya existen datos para evitar duplicados
            if (personaRepository.count() > 0) {
                log.info("✅ Ya existen datos en la base de datos. Saltando inicialización.");
                return;
            }

            // 1. Crear Personas Naturales (con IDs generados manualmente)
            UUID persona1Id = UUID.randomUUID();
            UUID persona2Id = UUID.randomUUID();
            UUID persona3Id = UUID.randomUUID();
            UUID persona4Id = UUID.randomUUID();

            PersonaNatural persona1 = PersonaNatural.builder()
                    .id(persona1Id) // ¡IMPORTANTE! Generar ID manualmente
                    .identificacion("1712345678")
                    .nombre("Juan Carlos Pérez")
                    .email("juan.perez@email.com")
                    .telefono("0998765432")
                    .apellido("Pérez González")
                    .fechaNacimiento(LocalDate.of(1985, 5, 15))
                    .genero("M")
                    .build();

            PersonaNatural persona2 = PersonaNatural.builder()
                    .id(persona2Id) // ¡IMPORTANTE! Generar ID manualmente
                    .identificacion("1754321890")
                    .nombre("María Fernanda López")
                    .email("maria.lopez@email.com")
                    .telefono("0987654321")
                    .apellido("López Martínez")
                    .fechaNacimiento(LocalDate.of(1990, 8, 22))
                    .genero("F")
                    .build();

            PersonaNatural persona3 = PersonaNatural.builder()
                    .id(persona3Id) // ¡IMPORTANTE! Generar ID manualmente
                    .identificacion("1722334455")
                    .nombre("Carlos Andrés Rodríguez")
                    .email("carlos.rodriguez@email.com")
                    .telefono("0976543210")
                    .apellido("Rodríguez Sánchez")
                    .fechaNacimiento(LocalDate.of(1978, 3, 10))
                    .genero("M")
                    .build();

            PersonaNatural persona4 = PersonaNatural.builder()
                    .id(persona4Id) // ¡IMPORTANTE! Generar ID manualmente
                    .identificacion("1766554433")
                    .nombre("Ana Lucía Torres")
                    .email("ana.torres@email.com")
                    .telefono("0965432109")
                    .apellido("Torres Valencia")
                    .fechaNacimiento(LocalDate.of(1995, 11, 30))
                    .genero("F")
                    .build();

            // 2. Crear Personas Jurídicas (con IDs generados manualmente)
            UUID empresa1Id = UUID.randomUUID();
            UUID empresa2Id = UUID.randomUUID();
            UUID empresa3Id = UUID.randomUUID();

            PersonaJuridica empresa1 = PersonaJuridica.builder()
                    .id(empresa1Id) // ¡IMPORTANTE! Generar ID manualmente
                    .identificacion("1798765434001")
                    .nombre("TECHNOLOGY SOLUTIONS S.A.")
                    .email("ventas@techsolutions.com")
                    .telefono("022334455")
                    .nombreComercial("Tech Solutions")
                    .razonSocial("TECHNOLOGY SOLUTIONS SOCIEDAD ANÓNIMA")
                    .representanteLegal("Roberto Mendoza")
                    .actividadEconomica("Desarrollo de Software")
                    .build();

            PersonaJuridica empresa2 = PersonaJuridica.builder()
                    .id(empresa2Id) // ¡IMPORTANTE! Generar ID manualmente
                    .identificacion("1798765435001")
                    .nombre("TRANSPORTES SEGUROS S.A.")
                    .email("contacto@transportesseguros.com")
                    .telefono("022556677")
                    .nombreComercial("Transportes Seguros")
                    .razonSocial("TRANSPORTES SEGUROS SOCIEDAD ANÓNIMA")
                    .representanteLegal("Marta González")
                    .actividadEconomica("Transporte de Carga")
                    .build();

            PersonaJuridica empresa3 = PersonaJuridica.builder()
                    .id(empresa3Id) // ¡IMPORTANTE! Generar ID manualmente
                    .identificacion("1798765436001")
                    .nombre("CONSTRUCCIONES MODERNAS S.A.")
                    .email("info@construccionesmodernas.com")
                    .telefono("022778899")
                    .nombreComercial("Construcciones Modernas")
                    .razonSocial("CONSTRUCCIONES MODERNAS SOCIEDAD ANÓNIMA")
                    .representanteLegal("Pedro Castillo")
                    .actividadEconomica("Construcción Civil")
                    .build();

            // Guardar todas las personas
            List<PersonaNatural> personasNaturales = personaRepository.saveAll(
                    Arrays.asList(persona1, persona2, persona3, persona4)
            );

            List<PersonaJuridica> personasJuridicas = personaRepository.saveAll(
                    Arrays.asList(empresa1, empresa2, empresa3)
            );

            log.info("✅ {} personas creadas exitosamente",
                    personasNaturales.size() + personasJuridicas.size());
            log.info("   - Personas Naturales: {}", personasNaturales.size());
            log.info("   - Personas Jurídicas: {}", personasJuridicas.size());

            // 3. Crear Motos para Personas Naturales (con IDs generados manualmente)
            Moto moto1 = Moto.builder()
                    .id(UUID.randomUUID()) // ¡IMPORTANTE! Generar ID manualmente
                    .placa("ABC-123")
                    .marca("Yamaha")
                    .modelo("MT-07")
                    .color("Azul")
                    .anioFabricacion(2023)
                    .propietario(persona1)
                    .cilindrada(689)
                    .tipoMoto("deportiva")
                    .tieneCasco(true)
                    .build();

            Moto moto2 = Moto.builder()
                    .id(UUID.randomUUID()) // ¡IMPORTANTE! Generar ID manualmente
                    .placa("XYZ-789")
                    .marca("Honda")
                    .modelo("CBR 650R")
                    .color("Rojo")
                    .anioFabricacion(2022)
                    .propietario(persona2)
                    .cilindrada(649)
                    .tipoMoto("deportiva")
                    .tieneCasco(false)
                    .build();

            Moto moto3 = Moto.builder()
                    .id(UUID.randomUUID()) // ¡IMPORTANTE! Generar ID manualmente
                    .placa("DEF-456")
                    .marca("Kawasaki")
                    .modelo("Ninja 400")
                    .color("Verde")
                    .anioFabricacion(2021)
                    .propietario(persona3)
                    .cilindrada(399)
                    .tipoMoto("deportiva")
                    .tieneCasco(true)
                    .build();

            Moto moto4 = Moto.builder()
                    .id(UUID.randomUUID()) // ¡IMPORTANTE! Generar ID manualmente
                    .placa("GHI-789")
                    .marca("Suzuki")
                    .modelo("GSX-R150")
                    .color("Blanco")
                    .anioFabricacion(2020)
                    .propietario(persona4)
                    .cilindrada(147)
                    .tipoMoto("deportiva")
                    .tieneCasco(true)
                    .build();

            // 4. Crear Autos Familiares (con IDs generados manualmente)
            AutoFamiliar auto1 = AutoFamiliar.builder()
                    .id(UUID.randomUUID()) // ¡IMPORTANTE! Generar ID manualmente
                    .placa("PBA-123")
                    .marca("Toyota")
                    .modelo("Corolla")
                    .color("Blanco")
                    .anioFabricacion(2023)
                    .propietario(persona1)
                    .numeroPuertas(4)
                    .capacidadMaletero(470)
                    .tieneAireAcondicionado(true)
                    .tipoCombustible("gasolina")
                    .build();

            AutoFamiliar auto2 = AutoFamiliar.builder()
                    .id(UUID.randomUUID()) // ¡IMPORTANTE! Generar ID manualmente
                    .placa("PBA-456")
                    .marca("Hyundai")
                    .modelo("Tucson")
                    .color("Gris")
                    .anioFabricacion(2022)
                    .propietario(persona2)
                    .numeroPuertas(5)
                    .capacidadMaletero(513)
                    .tieneAireAcondicionado(true)
                    .tipoCombustible("híbrido")
                    .build();

            AutoFamiliar auto3 = AutoFamiliar.builder()
                    .id(UUID.randomUUID()) // ¡IMPORTANTE! Generar ID manualmente
                    .placa("PBA-789")
                    .marca("Mazda")
                    .modelo("CX-5")
                    .color("Rojo")
                    .anioFabricacion(2021)
                    .propietario(persona3)
                    .numeroPuertas(5)
                    .capacidadMaletero(875)
                    .tieneAireAcondicionado(true)
                    .tipoCombustible("gasolina")
                    .build();

            // Autos para empresas
            AutoFamiliar autoEmpresa1 = AutoFamiliar.builder()
                    .id(UUID.randomUUID()) // ¡IMPORTANTE! Generar ID manualmente
                    .placa("ABC-001")
                    .marca("Ford")
                    .modelo("Ranger")
                    .color("Negro")
                    .anioFabricacion(2023)
                    .propietario(empresa1)
                    .numeroPuertas(4)
                    .capacidadMaletero(1200)
                    .tieneAireAcondicionado(true)
                    .tipoCombustible("diesel")
                    .build();

            AutoFamiliar autoEmpresa2 = AutoFamiliar.builder()
                    .id(UUID.randomUUID()) // ¡IMPORTANTE! Generar ID manualmente
                    .placa("ABC-002")
                    .marca("Chevrolet")
                    .modelo("Silverado")
                    .color("Blanco")
                    .anioFabricacion(2022)
                    .propietario(empresa2)
                    .numeroPuertas(4)
                    .capacidadMaletero(1500)
                    .tieneAireAcondicionado(true)
                    .tipoCombustible("diesel")
                    .build();

            AutoFamiliar autoEmpresa3 = AutoFamiliar.builder()
                    .id(UUID.randomUUID()) // ¡IMPORTANTE! Generar ID manualmente
                    .placa("ABC-003")
                    .marca("Toyota")
                    .modelo("Hilux")
                    .color("Plateado")
                    .anioFabricacion(2023)
                    .propietario(empresa3)
                    .numeroPuertas(4)
                    .capacidadMaletero(1100)
                    .tieneAireAcondicionado(true)
                    .tipoCombustible("diesel")
                    .build();

            // Guardar todos los vehículos
            List<Moto> motos = vehiculoRepository.saveAll(
                    Arrays.asList(moto1, moto2, moto3, moto4)
            );

            List<AutoFamiliar> autos = vehiculoRepository.saveAll(
                    Arrays.asList(auto1, auto2, auto3, autoEmpresa1, autoEmpresa2, autoEmpresa3)
            );

            log.info("✅ {} vehículos creados exitosamente", motos.size() + autos.size());
            log.info("   - Motos: {}", motos.size());
            log.info("   - Autos Familiares: {}", autos.size());

            // 5. Mostrar información de ejemplo para pruebas
            log.info("🎉 Inicialización completada exitosamente!");
            log.info("");
            log.info("📋 DATOS DE EJEMPLO PARA PRUEBAS:");
            log.info("");
            log.info("👤 PERSONAS NATURALES:");
            log.info("   Cédula: 1712345678 - Juan Carlos Pérez - ID: {}", persona1Id);
            log.info("   Cédula: 1754321890 - María Fernanda López - ID: {}", persona2Id);
            log.info("   Cédula: 1722334455 - Carlos Andrés Rodríguez - ID: {}", persona3Id);
            log.info("   Cédula: 1766554433 - Ana Lucía Torres - ID: {}", persona4Id);
            log.info("");
            log.info("🏢 PERSONAS JURÍDICAS:");
            log.info("   RUC: 1798765434001 - TECHNOLOGY SOLUTIONS S.A. - ID: {}", empresa1Id);
            log.info("   RUC: 1798765435001 - TRANSPORTES SEGUROS S.A. - ID: {}", empresa2Id);
            log.info("   RUC: 1798765436001 - CONSTRUCCIONES MODERNAS S.A. - ID: {}", empresa3Id);
            log.info("");
            log.info("🏍️  MOTOS:");
            log.info("   Placa: ABC-123 - Yamaha MT-07 (Juan Carlos Pérez)");
            log.info("   Placa: XYZ-789 - Honda CBR 650R (María Fernanda López)");
            log.info("   Placa: DEF-456 - Kawasaki Ninja 400 (Carlos Andrés Rodríguez)");
            log.info("   Placa: GHI-789 - Suzuki GSX-R150 (Ana Lucía Torres)");
            log.info("");
            log.info("🚗 AUTOS:");
            log.info("   Placa: PBA-123 - Toyota Corolla (Juan Carlos Pérez)");
            log.info("   Placa: PBA-456 - Hyundai Tucson (María Fernanda López)");
            log.info("   Placa: PBA-789 - Mazda CX-5 (Carlos Andrés Rodríguez)");
            log.info("   Placa: ABC-001 - Ford Ranger (TECHNOLOGY SOLUTIONS S.A.)");
            log.info("   Placa: ABC-002 - Chevrolet Silverado (TRANSPORTES SEGUROS S.A.)");
            log.info("   Placa: ABC-003 - Toyota Hilux (CONSTRUCCIONES MODERNAS S.A.)");
        };
    }
}
