package com.gestion.estudio.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gestion.estudio.entidades.Sala;
import com.gestion.estudio.repositories.interfaces.I_SalaRepository;

@Service
public class SalaService {
    private final I_SalaRepository salaRepository;

    public SalaService(I_SalaRepository salaRepository){
        this.salaRepository = salaRepository;
    }

    public Sala guardarSala(Sala sala) throws SQLException{
        if(sala.getId() != 0){
            salaRepository.update(sala);
            return sala;
        }else{
            salaRepository.create(sala);
            return sala;
        }
    }

    public Sala buscarSalaPorId(int id) throws SQLException{
        return salaRepository.findById(id);
    }

    public Sala buscarSalaPorNombre(String nombre) throws SQLException{
        return salaRepository.findByNombre(nombre);
    }

    public List<Sala> buscarTodasLasSalas() throws SQLException{
        return salaRepository.findAll();
    }

    public int eliminarSala(int id) throws SQLException{ 
        return salaRepository.delete(id);
    }
}
