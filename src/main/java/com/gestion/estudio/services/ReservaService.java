package com.gestion.estudio.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gestion.estudio.entidades.Reserva;
import com.gestion.estudio.repositories.interfaces.I_ReservaRepository;

@Service
public class ReservaService {
    private final I_ReservaRepository reservaRepository;

    public ReservaService(I_ReservaRepository reservaRepository){
        this.reservaRepository = reservaRepository;
    }

    public Reserva guardarReserva(Reserva reserva) throws SQLException{
        if(reserva.getId() != 0){
            reservaRepository.update(reserva);
            return reserva;
        }else{
            reservaRepository.create(reserva);
            return reserva;
        }
    }

    public Reserva buscarReservaPorId(int id) throws SQLException{
        return reservaRepository.findById(id);
    }

    public List<Reserva> buscarReservasPorBanda(int idBanda) throws SQLException{
        return reservaRepository.findByBanda(idBanda);
    }

    public List<Reserva> buscarTodasLasReservas() throws SQLException{
        return reservaRepository.findAll();
    }

    public int eliminarReserva(int id) throws SQLException{
        return reservaRepository.delete(id);
    }
}
