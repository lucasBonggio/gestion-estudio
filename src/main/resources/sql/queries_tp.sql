USE freedb_estudio_tpf;


-- TRAEMOS TODAS LAS RESERVAS, ELIGIENDO LOS DATOS MAS SIGNIFICATIVOS
SELECT r.id AS id_reserva,
	   b.nombre AS banda,
       sa.nombre AS sala,
       r.fecha,
       r.hora_inicio,
       r.hora_fin,
       r.precio_final
FROM reservas r
JOIN bandas b ON r.id_banda = b.id
JOIN salas sa ON r.id_sala = sa.id;

-- TRAER TODAS LAS RESERVAS QUE FUERON PARA SALAS DE GRABACIÓN
SELECT r.id AS id_reserva,
	   b.nombre AS banda,
       sa.tipo AS tipo_sala,
       r.fecha,
       r.precio_final
FROM reservas r
JOIN bandas b ON r.id_banda = b.id
JOIN salas sa ON r.id_sala = sa.id
WHERE sa.tipo = "Grabación";

-- TRAER Y ORDENAR LAS VECES QUE CADA BANDA VINO A ENSAYAR
SELECT b.nombre,
       COUNT(*) AS cantidad_ensayos
FROM bandas b 
JOIN reservas r ON b.id = r.id_banda
JOIN salas sa ON sa.id = r.id_sala
WHERE sa.tipo = "Ensayo"
GROUP BY b.nombre
ORDER BY cantidad_ensayos DESC;

-- TRAER TODAS LAS BANDAS QUE PASARON POR EL ESTUDIO Y CUANDO HA GASTADO CADA UNA
SELECT b.nombre AS banda,
	   SUM(r.precio_final) AS total_gastado
FROM reservas r
JOIN bandas b ON r.id_banda = b.id
GROUP BY b.id;

-- LA CANTIDAD DE VECES QUE SE ALQUILO UN SERVICIO EN TODAS LAS RESERVAS

SELECT s.nombre AS servicio,
       SUM(rs.cantidad) AS total_alquilado
FROM reserva_servicios rs
JOIN servicios s ON rs.id_servicio = s.id
GROUP BY s.nombre
ORDER BY total_alquilado DESC;

-- LA CANTIDAD DE VECES QUE SE ALQUILO CADA SALA Y TRAERLAS ORDENADAS
SELECT s.nombre,
	   COUNT(*) veces_alquiladas
FROM salas s
JOIN reservas r ON r.id_sala = s.id
GROUP BY s.id
ORDER BY veces_alquiladas DESC;

-- TRAER EL ID DE LA RESERVA Y EL NOMBRE DE LA BANDA QUE NO HAYA ALQUILADO NINGUN SERVICIO EN ALGUNA RESERVA
SELECT r.id AS id_reserva,
	   b.nombre AS banda
FROM reservas r 
JOIN bandas b ON b.id = r.id
LEFT JOIN reserva_servicios rs ON r.id = rs.id_reserva
WHERE id_reserva IS NULL;

-- VER BANDA, FECHA Y HORA DE CADA RESERVA ORDERNADAS CRONOLOGICAMENTE.
SELECT b.nombre,
	   r.fecha,
	   r.hora_inicio
FROM reservas r
JOIN bandas b ON r.id_banda = b.id
ORDER BY r.fecha DESC;


-- TRAER TODAS LAS BANDAS QUE TIENEN OBSERVACIONES
SELECT nombre,
	   observaciones
FROM bandas
WHERE observaciones IS NOT NULL;



		