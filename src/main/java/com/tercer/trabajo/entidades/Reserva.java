package com.tercer.trabajo.entidades;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

    private int id;
    private int idBanda;
    private int idSala;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private double precioFinal;
}
