package com.tercer.trabajo.repositories.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.tercer.trabajo.entidades.Reserva;

public interface I_ReservaRepository {
    void create(Reserva reserva) throws SQLException;
    Reserva findById(int id) throws SQLException;
    List<Reserva> findAll() throws SQLException;
    List<Reserva> findByBanda(int idBanda) throws SQLException;
    int update(Reserva reserva, int idReserva) throws SQLException;
    int delete(int id) throws SQLException;
}