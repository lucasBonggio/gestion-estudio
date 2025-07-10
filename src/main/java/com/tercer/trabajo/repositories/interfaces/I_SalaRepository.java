package com.tercer.trabajo.repositories.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.tercer.trabajo.entidades.Sala;

public interface I_SalaRepository {
    void create(Sala sala) throws SQLException;
    Sala findById(int id) throws SQLException;
    Sala findByReserva(int idReserva) throws SQLException;
    List<Sala> findAll() throws SQLException;
    int update(Sala sala, int id) throws SQLException;
    int delete(int id) throws SQLException;
}
