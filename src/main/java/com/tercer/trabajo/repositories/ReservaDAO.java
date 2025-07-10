package com.tercer.trabajo.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.tercer.trabajo.entidades.Reserva;
import com.tercer.trabajo.repositories.interfaces.I_ReservaRepository;

@Repository
public class ReservaDAO implements I_ReservaRepository {

    private final DataSource DATASOURCE;

    private final String SQL_CREATE = 
        "INSERT INTO reservas(id_banda, id_sala, fecha, hora_inicio, hora_fin, precio_final) VALUES(?, ?, ?, ?, ?, ?)";
    private final String SQL_FIND_BY_ID = 
        "SELECT * FROM reservas WHERE id = ?";
    private final String SQL_FIND_ALL =
        "SELECT * FROM reservas";
    private final String SQL_FIND_BY_BANDA =
        "SELECT r.* FROM reservas r JOIN bandas b ON r.id_banda = b.id WHERE r.id_banda = ?";
    private final String SQL_UPDATE = 
        "UPDATE reservas SET id_banda = ?, id_sala = ?, fecha = ?, hora_inicio = ?, hora_fin = ?, precio_final = ? WHERE id = ?";
    private final String SQL_DELETE =
        "DELETE FROM reservas WHERE id = ?";

    public ReservaDAO(DataSource dataSource){
        this.DATASOURCE = dataSource;
    }

    public void create(Reserva reserva) throws SQLException{
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, reserva.getIdBanda());
            ps.setInt(2, reserva.getIdSala());
            ps.setDate(3, Date.valueOf(reserva.getFecha()));
            ps.setTime(4, Time.valueOf(reserva.getHoraInicio()));
            ps.setTime(5, Time.valueOf(reserva.getHoraFin()));
            ps.setDouble(6, reserva.getPrecioFinal());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if(keys.next()){
                    reserva.setId(keys.getInt(1));
                }
            }
        } 
    }

    public Reserva findById(int id) throws SQLException {
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

    public List<Reserva> findAll() throws SQLException{
        List<Reserva> reservas = new ArrayList<>();

        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL)) {
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    reservas.add(mapRow(rs));
                }
            } 
        }
        return reservas; 
    }

    public List<Reserva> findByBanda(int idBanda) throws SQLException{
        List<Reserva> reservas = new ArrayList<>();

        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_BANDA)) {
                ps.setInt(1, idBanda);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    reservas.add(mapRow(rs));
                }
            } 
        }
        return reservas;
    }

    public int update(Reserva reserva, int idReserva) throws SQLException{
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, reserva.getIdBanda());
            ps.setInt(2, reserva.getIdSala());
            ps.setDate(3, Date.valueOf(reserva.getFecha()));
            ps.setTime(4, Time.valueOf(reserva.getHoraInicio()));
            ps.setTime(5, Time.valueOf(reserva.getHoraFin()));
            ps.setDouble(6, reserva.getPrecioFinal());

            ps.setInt(7, idReserva);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas;
        }
    }
    
    public int delete(int id) throws SQLException{
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas;

        }
    }

    public Reserva mapRow(ResultSet rs) throws SQLException{
        Reserva reserva = new Reserva();
        reserva.setId(rs.getInt(1));
        reserva.setIdBanda(rs.getInt(2));
        reserva.setIdSala(rs.getInt(3));
        reserva.setFecha((rs.getDate(4)).toLocalDate());
        reserva.setHoraInicio(rs.getTime(5).toLocalTime());
        reserva.setHoraFin(rs.getTime(6).toLocalTime());
        reserva.setPrecioFinal(rs.getDouble(7));

        return reserva;
    }
}
