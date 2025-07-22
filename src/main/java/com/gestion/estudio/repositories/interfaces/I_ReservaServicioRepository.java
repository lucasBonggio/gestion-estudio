package com.gestion.estudio.repositories.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.gestion.estudio.entidades.ReservaServicio;

public interface I_ReservaServicioRepository {
    void create(ReservaServicio servicioReserva) throws SQLException;
    ReservaServicio findById(int idReserva, int idServicio) throws SQLException;
    List<ReservaServicio> findAll() throws SQLException;
    List<ReservaServicio> findByIdReserva(int idReserva) throws SQLException;
    int update(ReservaServicio servicioReserva) throws SQLException;
    int delete(int idReserva, int idServicio) throws SQLException; 
}
