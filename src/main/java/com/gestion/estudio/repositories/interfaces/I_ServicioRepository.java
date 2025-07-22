package com.gestion.estudio.repositories.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.gestion.estudio.entidades.Servicio;

public interface I_ServicioRepository {
    void create(Servicio servicio) throws SQLException;
    Servicio findById(int id) throws SQLException;
    Servicio findByNombre(String nombre) throws SQLException;
    List<Servicio> findAll() throws SQLException;
    int update(Servicio servicio) throws SQLException;
    int delete(int id) throws SQLException;
}
