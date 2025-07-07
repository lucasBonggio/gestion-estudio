DROP DATABASE IF EXISTS freedb_estudio_tpf;
CREATE DATABASE freedb_estudio_tpf;
USE freedb_estudio_tpf;

CREATE TABLE salas(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(200) NOT NULL,
    capacidad INT NOT NULL CHECK(capacidad BETWEEN 1 AND 10),
    tipo ENUM('Ensayo', 'Grabación') NOT NULL,
    precio_hora DOUBLE NOT NULL
);

CREATE TABLE servicios(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(200) NOT NULL,
    precio DOUBLE NOT NULL
);

CREATE TABLE bandas(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(200) NOT NULL,
    genero VARCHAR(200) NOT NULL,
    cantidad_musicos INT NOT NULL,
    contacto VARCHAR(200) NOT NULL,
    observaciones VARCHAR(200)
);

CREATE TABLE reserva_servicios(
	id_reserva INT,
    id_servicio INT,
    cantidad INT NOT NULL, 
    PRIMARY KEY(id_reserva, id_servicio)
);

CREATE TABLE reservas(
	id INT PRIMARY KEY AUTO_INCREMENT,
    id_banda INT NOT NULL,
    id_sala INT NOT NULL,
    fecha DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    precio_final DOUBLE NOT NULL
);

ALTER TABLE reserva_servicios
ADD CONSTRAINT fk_reserva
FOREIGN KEY (id_reserva) REFERENCES reservas(id),
ADD CONSTRAINT fk_servicio
FOREIGN KEY (id_servicio) REFERENCES servicios(id);

ALTER TABLE reservas
ADD CONSTRAINT fk_sala
FOREIGN KEY (id_sala) REFERENCES salas(id),
ADD CONSTRAINT fk_banda_reserva
FOREIGN KEY (id_banda) REFERENCES bandas(id);
