package com.tercer.trabajo.entidades;

import com.tercer.trabajo.enums.Tipo;

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
