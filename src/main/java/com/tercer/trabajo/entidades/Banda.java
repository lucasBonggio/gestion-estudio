package com.tercer.trabajo.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banda {
    
    private int id;
    private String nombre;
    private String genero;
    private int cantidadMusicos;
    private String contacto;
    private String observaciones;
}
