package com.tercer.trabajo.repositories.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.tercer.trabajo.entidades.Banda;

public interface I_BandaRepository {
    void create(Banda banda) throws SQLException;
    Banda findById(int id) throws SQLException;
    List<Banda> findAll() throws SQLException;
    Banda findByNombre(String nombre) throws SQLException;
    int update(Banda banda) throws SQLException;
    int delete(int id) throws SQLException;
}
