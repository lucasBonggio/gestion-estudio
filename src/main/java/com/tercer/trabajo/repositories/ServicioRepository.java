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

import com.tercer.trabajo.entidades.Servicio;
import com.tercer.trabajo.repositories.interfaces.I_ServicioRepository;

@Repository
public class ServicioRepository implements I_ServicioRepository {

    private final DataSource DATASOURCE;

    private static final String SQL_CREATE =
        "INSERT INTO servicios(nombre, precio) VALUES(?, ?)";
    private static final String  SQL_FIND_BY_ID =
        "SELECT * FROM servicios WHERE id = ?";
    private static final String SQL_FIND_BY_NOMBRE = 
        "SELECT * FROM servicios WHERE nombre = ?";
    private static final String SQL_FIND_ALL = 
        "SELECT * FROM servicios";
    private static final String SQL_UPDATE = 
        "UPDATE servicios SET nombre = ?, precio = ? WHERE id = ?";
    private static final String SQL_DELETE = 
        "DELETE FROM servicios WHERE id = ?";
    
    public ServicioRepository(DataSource dataSource){
        this.DATASOURCE = dataSource;
    }

    @Override
    public void create(Servicio servicio) throws SQLException {
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, servicio.getNombre());
            ps.setDouble(2, servicio.getPrecio());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if(keys.next()){
                    servicio.setId(keys.getInt(1));
                }
            }
        }
    }

    @Override
    public Servicio findById(int id) throws SQLException {
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID, Statement.RETURN_GENERATED_KEYS)) {
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
    public Servicio findByNombre(String nombre) throws SQLException {
        List<Servicio> servicios = new ArrayList<>();

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
    public List<Servicio> findAll() throws SQLException {
        List<Servicio> servicios = new ArrayList<>();
        
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
                ResultSet rs = ps.executeQuery()) {
            while(rs.next()){
                servicios.add(mapRow(rs));
            }
        }
        return servicios;
    }

    @Override
    public int update(Servicio servicio) throws SQLException {
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            
            ps.setString(1, servicio.getNombre());
            ps.setDouble(2, servicio.getPrecio());

            ps.setInt(3, servicio.getId());
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

    public Servicio mapRow(ResultSet rs) throws SQLException{
        Servicio servicio = new Servicio();
        servicio.setId(rs.getInt("id"));
        servicio.setNombre(rs.getString("nombre"));
        servicio.setPrecio(rs.getDouble("precio"));

        return servicio;
    }
}
