package ec.edu.espe.clientes.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {
    private final RabbitTemplate rabbitTemplate;

    public static final String ACTION_CREATE = "CREATE";
    public static final String ACTION_UPDATE = "UPDATE";
    public static final String ACTION_DELETE = "DELETE";

    public static final String ENTITY_ZONE = "ZONE";
    public static final String ENTITY_SPACE = "SPACE";

    public static final String SEVERITY_INFO = "INFO";

    public void sendNotification(String action, String entityType,
                                 UUID entityId, String message, Map<String, Object> data) {

        NotificationEvent event = NotificationEvent.builder()
                .id(UUID.randomUUID())
                .microservice("person-service") // o "persona-service"
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .message(message)
                .timestamp(LocalDateTime.now()) // Esto se serializará automáticamente
                .data(data != null ? data : new HashMap<>())
                .severity(SEVERITY_INFO)
                .build();

        try {
            // Verificar el evento antes de enviar
            log.debug("Enviando evento: {}", event);
            log.debug("Timestamp: {}", event.getTimestamp());

            rabbitTemplate.convertAndSend(
                    "notifications.exchange",
                    "notification.routingkey",
                    event
            );

            log.info("Notificación enviada: {} - {}", action, entityType);
        } catch (Exception e) {
            log.error("Error al enviar notificación: {}", e.getMessage(), e);
        }
    }

    public void notificationPersonCreated(UUID personaId, String nombre, String apellido) {
        Map<String, Object> data = new HashMap<>();
        data.put("nombre", nombre);
        data.put("apellido", apellido);

        sendNotification(ACTION_CREATE,
                "ENTITY_PERSON", personaId, "Persona creada", data);
    }
}
