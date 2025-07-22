package com.gestion.estudio.repositories.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.gestion.estudio.entidades.Sala;

public interface I_SalaRepository {
    void create(Sala sala) throws SQLException;
    Sala findById(int id) throws SQLException;
    Sala findByNombre(String nombre) throws SQLException;
    List<Sala> findAll() throws SQLException;
    int update(Sala sala) throws SQLException;
    int delete(int id) throws SQLException;
}
