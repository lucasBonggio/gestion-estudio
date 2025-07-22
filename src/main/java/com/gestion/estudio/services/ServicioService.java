package com.gestion.estudio.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gestion.estudio.entidades.Servicio;
import com.gestion.estudio.repositories.interfaces.I_ServicioRepository;

@Service
public class ServicioService {

    private final I_ServicioRepository servicioRepository;

    public ServicioService(I_ServicioRepository servicioRepository){
        this.servicioRepository = servicioRepository;
    }

    public Servicio guardarServicio(Servicio servicio) throws SQLException{
        if(servicio.getId() != 0){
            servicioRepository.update(servicio);
            return servicio;
        }else{
            servicioRepository.create(servicio);
            return servicio;
        }
    }

    public Servicio buscarServicioPorId(int id) throws SQLException{
        return servicioRepository.findById(id);
    }

    public Servicio buscarServicioPorNombre(String nombre) throws SQLException{
        return servicioRepository.findByNombre(nombre);
    }

    public List<Servicio> buscarTodosLosServicios() throws SQLException{
        return servicioRepository.findAll();
    }

    public int eliminarServicio(int id) throws SQLException{
        return servicioRepository.delete(id);
    }
}
