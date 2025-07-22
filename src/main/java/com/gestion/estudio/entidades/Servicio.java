package com.gestion.estudio.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Servicio {
    
    private int id;
    private String nombre;
    private double precio;
}
