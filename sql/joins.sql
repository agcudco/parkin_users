SELECT
    p.id,
    p.identificacion,
    p.nombre,
    pn.apellido,
    p.email,
    p.telefono,
    pn.fecha_nacimiento,
    pn.genero,
    p.activo,
    p.fecha_creacion
FROM personas p
         INNER JOIN personas_naturales pn
                    ON p.id = pn.persona_id
WHERE p.tipo_persona = 'NATURAL'
ORDER BY p.nombre;
