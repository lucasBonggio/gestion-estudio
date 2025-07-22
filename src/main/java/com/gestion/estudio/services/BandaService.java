package com.gestion.estudio.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gestion.estudio.entidades.Banda;
import com.gestion.estudio.repositories.interfaces.I_BandaRepository;

@Service
public class BandaService {

    private final I_BandaRepository bandaRepository;

    public BandaService(I_BandaRepository bandaRepository){
        this.bandaRepository = bandaRepository;
    }

    public Banda guardarBanda(Banda banda) throws SQLException {
        if(banda.getId() != 0){
            bandaRepository.update(banda);
            return banda;
        }else{
            bandaRepository.create(banda);
            return banda;
        }
    }
    
    public Banda buscarBandaPorId(int id) throws SQLException{
        return bandaRepository.findById(id);
    }

    public Banda buscarBandaPorNombre(String nombre) throws SQLException{
        return bandaRepository.findByNombre(nombre);
    }

    public List<Banda> buscarTodasLasBandas() throws SQLException{
        return bandaRepository.findAll();
    }

    public int eliminarBanda(int id) throws SQLException{
        return bandaRepository.delete(id);
    }
}
