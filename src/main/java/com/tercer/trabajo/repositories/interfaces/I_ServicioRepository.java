package com.tercer.trabajo.repositories.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.tercer.trabajo.entidades.Servicio;

public interface I_ServicioRepository {
    void create(Servicio servicio) throws SQLException;
    Servicio findById(int id) throws SQLException;
    List<Servicio> findByReserva(int idReserva) throws SQLException;
    List<Servicio> findAll() throws SQLException;
    int update(Servicio servici, int idServicio) throws SQLException;
    int delete(int id) throws SQLException;
}
