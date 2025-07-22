package com.gestion.estudio.entidades;

import com.gestion.estudio.enums.Tipo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sala {
    
    private int id;
    private String nombre;
    private int capacidad;
    private Tipo tipo;
    private double precioHora;
}
