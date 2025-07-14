package com.tercer.trabajo.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.tercer.trabajo.entidades.Sala;
import com.tercer.trabajo.enums.Tipo;
import com.tercer.trabajo.repositories.interfaces.I_SalaRepository;

@Repository
public class SalaRepository implements I_SalaRepository {

    private final DataSource DATASOURCE;

    private static final String SQL_CREATE = 
        "INSERT INTO salas(nombre, capacidad, tipo, precio_hora) VALUES(?, ?, ?, ?)";
    private static final String SQL_FIND_BY_ID = 
        "SELECT * FROM salas WHERE id = ?";
    private static final String SQL_FIND_BY_NOMBRE = 
        "SELECT * FROM salas WHERE nombre = ?";
    private static final String SQL_FIND_ALL = 
        "SELECT * FROM salas";
    private static final String SQL_UPDATE = 
        "UPDATE salas SET nombre = ?, capacidad = ?, tipo = ?, precio_hora = ? WHERE id = ?";
    private static final String SQL_DELETE = 
        "DELETE FROM salas WHERE id = ?";

    public SalaRepository(DataSource dataSource){
        this.DATASOURCE = dataSource;
    }

    @Override
    public void create(Sala sala) throws SQLException {
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, sala.getNombre());
            ps.setInt(2, sala.getCapacidad());
            ps.setString(3, sala.getTipo().name());
            ps.setDouble(4, sala.getPrecioHora());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if(keys.next()){
                    sala.setId(keys.getInt(1));
                }
            }
        } 
    }

    @Override
    public Sala findById(int id) throws SQLException {
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    return mapRow(rs);
                }
            } 
        } 
        return null;
    }

    @Override
    public Sala findByNombre(String nombre) throws SQLException {
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_NOMBRE, Statement.RETURN_GENERATED_KEYS)) {
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
    public List<Sala> findAll() throws SQLException {
        List<Sala> salas = new ArrayList<>();

        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
                ResultSet rs = ps.executeQuery()) {
            
                while(rs.next()){
                    salas.add(mapRow(rs));
                }
        } 
        return salas;
    }

    @Override
    public int update(Sala sala) throws SQLException {
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, sala.getNombre());
            ps.setInt(2, sala.getCapacidad());
            ps.setString(3, sala.getTipo().name());
            ps.setDouble(4, sala.getPrecioHora());
            
            ps.setInt(5, sala.getId());

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

    public Sala mapRow(ResultSet rs) throws SQLException{
        Sala sala = new Sala();
        sala.setId(rs.getInt("id"));
        sala.setNombre(rs.getString("nombre"));
        sala.setCapacidad(rs.getInt("capacidad"));
        sala.setTipo(Tipo.valueOf(rs.getString("tipo")));
        sala.setPrecioHora(rs.getDouble("precio_hora"));

        return sala;
    }
}
