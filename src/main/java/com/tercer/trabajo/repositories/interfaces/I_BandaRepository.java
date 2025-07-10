package com.tercer.trabajo.repositories.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.tercer.trabajo.entidades.Banda;

public interface I_BandaRepository {
    void create(Banda banda) throws SQLException;
    Banda findById(int id) throws SQLException;
    List<Banda> findAll() throws SQLException;
    Banda findByReserva(int idReserva) throws SQLException;
    int update(Banda banda, int idBanda) throws SQLException;
    int delete(int id) throws SQLException;
}
