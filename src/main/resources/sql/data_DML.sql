USE freedb_estudio_tpf;
-- CREAMOS LAS SALAS
INSERT INTO salas(nombre, capacidad, tipo, precio_hora) VALUES 
('Sala 1', 5, 'Ensayo', 30000),
('Sala 2', 7, 'Ensayo', 45000),
('Sala 3', 5, 'Grabación', 75000),
('Sala 4', 7, 'Grabación', 105000);

-- CREAMOS BANDAS
INSERT INTO bandas(nombre, genero, cantidad_musicos, contacto) VALUES
('Sangre Rebelde', 'Punk', 3, 'sangre@rebelde.com'),
('Flema', 'Punk', 5, 'Ricky - 433873341'),
('Los inconscientes', 'Rock', 5, 'Jose maría - 439887876'),
('Parcas sangrientas', 'Metal pesado', 4, 'Ricardo - 1113983498'),
('Ecos del Metal', 'Metal', 6, 'ecos@metal.com'),
('2 minutos', 'Punk', 5, 'Mosca - 1123456453'),
('Susurros Urbanos', 'Indie', 3, 'susurros@urbanos.com'),
('Brigrada metalica', 'Metal pesad', 4, ' Carlos - 1123456675');

-- AGREGAMOS ALGUNAS OBSERVACIONES
UPDATE bandas SET observaciones = "Llegaron 15 minutos tarde" WHERE id = 3;
UPDATE bandas SET observaciones = "No respetan las cosas del estudio" WHERE id = 5; 
UPDATE bandas SET observaciones = "Muy hippones" WHERE id = 7;
UPDATE bandas SET observaciones = "Son una masa" WHERE id = 6;


-- AGREGAMOS LOS SERVICIOS QUE VA A OFRECER EL ESTUDIO
INSERT INTO servicios(nombre, precio) VALUES
('Alquiler de Instrumentos', 20000),
('Alquiler de Amplificadores', 45000),
('Técnico musical', 50000),
('Cuerdas o parches', 30000);

-- CREAMOS VARIAS RESERVAS
INSERT INTO reservas(id_banda, id_sala, fecha, hora_inicio, hora_fin, precio_final) VALUES
(1, 1, '2025-07-01', '14:00:00', '16:00:00', 60000), 
(4, 1, '2024-06-22', '20:00:00', '22:00:00', 60000),
(4, 3, '2024-11-20', '19:00:00', '21:00:00', 15000),
(4, 1, '2025-01-20', '15:00:00', '17:00:00', 60000),
(2, 2, '2025-07-01', '10:00:00', '12:00:00', 90000),
(3, 3, '2025-07-02', '15:00:00', '17:00:00', 150000), 
(4, 4, '2025-07-02', '18:00:00', '21:00:00', 315000), 
(5, 1, '2025-07-03', '12:00:00', '14:00:00', 60000),
(6, 2, '2025-07-03', '17:00:00', '19:00:00', 90000),
(7, 3, '2025-07-04', '20:00:00', '22:00:00', 150000),
(8, 4, '2025-07-11', '16:00:00', '18:00:00', 210000),
(8, 3, '2025-07-13', '12:00:00', '14:00:00', 150000),
(5, 3, '2025-07-10', '10:00:00', '12:00:00', 150000),
(5, 4, '2025-07-12', '14:00:00', '17:00:00', 315000),
(5, 3, '2025-07-14', '18:00:00', '20:00:00', 150000);

-- LE SUMAMOS LOS SERVICIOS QUE CADA BANDA ALQUILO 
INSERT INTO reserva_servicios(id_reserva, id_servicio, cantidad) VALUES
(1, 1, 1),
(2, 1, 1),
(2, 4, 1),
(3, 1, 1),
(3, 2, 1),
(3, 3, 1),
(4, 2, 1),
(4, 3, 2),
(6, 4, 2),
(7, 3, 1),
(7, 4, 1);

-- SUMAR EL PRECIO DE CADA SERVICIO ALQUILADO A LA RESERVA CORRESPONDIENTE
UPDATE reservas r
SET r.precio_final = r.precio_final + (
        SELECT IFNULL (SUM(s.precio * rs.cantidad), 0) -- USO IFNULL PARA SETEAR EL precio_final EN 0 CUANDO ÉSTE SEA NULL Y ASÍ PODER SUMARLE LOS SERVICIOS
        FROM reserva_servicios rs
            JOIN servicios s ON rs.id_servicio = s.id
        WHERE rs.id_reserva = r.id
    )
WHERE r.id <= 12;


