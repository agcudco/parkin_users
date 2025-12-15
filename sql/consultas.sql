WITH persona_base AS (
INSERT INTO personas (
    activo,
    fecha_creacion,
    telefono,
    identificacion,
    tipo_persona,
    email,
    nombre
)
VALUES (
    true,
    now(),
    '0999999999',
    '0102030405',
    'NATURAL',
    'persona@email.com',
    'Juan'
    )
    RETURNING id
    )
INSERT INTO personas_naturales (
    persona_id,
    apellido,
    fecha_nacimiento,
    genero
)
SELECT
    id,
    'Pérez',
    DATE '1995-05-20',
    'M'
FROM persona_base;
