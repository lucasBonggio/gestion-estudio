package com.gestion.estudio.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.gestion.estudio.entidades.Banda;
import com.gestion.estudio.repositories.interfaces.I_BandaRepository;

@Repository
public class BandaRepository implements I_BandaRepository {
    
    private final DataSource DATASOURCE;

    private static final String SQL_CREATE =
        "INSERT INTO bandas(nombre, genero, cantidad_musicos, contacto, observaciones) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_FIND_BY_ID=
        "SELECT * FROM bandas WHERE id = ?";
    private static final String SQL_FIND_ALL =
        "SELECT * FROM bandas";
    private static final String SQL_FIND_BY_NOMBRE =
        "SELECT * FROM bandas WHERE nombre = ?";
    private static final String SQL_UPDATE = 
        "UPDATE bandas SET nombre = ?, genero = ?, cantidad_musicos = ?, contacto = ?, observaciones = ? WHERE id = ?";
    private static final String SQL_DELETE =
        "DELETE FROM bandas WHERE id = ?";

    public BandaRepository(DataSource dataSource){
        this.DATASOURCE = dataSource;
    }

    @Override
    public void create(Banda banda) throws SQLException {
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, banda.getNombre());
            ps.setString(2, banda.getGenero());
            ps.setInt(3, banda.getCantidadMusicos());
            ps.setString(4, banda.getContacto());
            ps.setString(5, banda.getObservaciones());
            ps.executeUpdate();
            
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if(keys.next()){
                    banda.setId(keys.getInt(1));
                }
            }
        }
    }

    @Override
    public Banda findById(int id) throws SQLException {
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return mapRow(rs);
                }
            }
            
        } 
        return null;
    }
    
    @Override
    public List<Banda> findAll() throws SQLException {
        List<Banda> bandas = new ArrayList<>();
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
                ResultSet rs = ps.executeQuery()) {

                while(rs.next()){
                    bandas.add(mapRow(rs));
                }    
        }
        return bandas; 
    }

    @Override
    public Banda findByNombre(String nombre) throws SQLException {
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_NOMBRE)) {
            ps.setString(1, nombre);
            
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    return mapRow(rs);
                }
            }    
        }
        return null;
    }

    @Override
    public int update(Banda banda) throws SQLException {
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, banda.getNombre());
            ps.setString(2, banda.getGenero());
            ps.setInt(3, banda.getCantidadMusicos());
            ps.setString(4, banda.getContacto());
            ps.setString(5, banda.getObservaciones());

            ps.setInt(6, banda.getId());
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas;
        }
    }

    @Override
    public int delete(int id) throws SQLException {
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas;
        }
    }

    public Banda mapRow(ResultSet rs) throws SQLException{
        Banda banda = new Banda();
        banda.setId(rs.getInt("id"));
        banda.setNombre(rs.getString("nombre"));
        banda.setGenero(rs.getString("genero"));
        banda.setCantidadMusicos(rs.getInt("cantidad_musicos"));
        banda.setContacto(rs.getString("contacto"));
        banda.setObservaciones(rs.getString("observaciones"));
        
        return banda;
    }
}