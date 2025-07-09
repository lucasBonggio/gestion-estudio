package com.tercer.trabajo.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaServicio {
    
    private int idReserva;
    private int idServicio;
    private int cantidad;
}
