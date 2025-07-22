package com.gestion.estudio.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gestion.estudio.entidades.Reserva;
import com.gestion.estudio.entidades.ReservaServicio;
import com.gestion.estudio.entidades.Servicio;
import com.gestion.estudio.repositories.interfaces.I_ReservaServicioRepository;

@Service
public class ReservaServicioService {
    private final I_ReservaServicioRepository reservaServicioRepository;
    private final ReservaService reservaService;
    private final ServicioService servicioService;

    public ReservaServicioService(I_ReservaServicioRepository reservaServicioRepository, ReservaService reservaService, ServicioService servicioService){
        this.reservaServicioRepository = reservaServicioRepository;
        this.reservaService = reservaService;
        this.servicioService = servicioService;
    }

    public ReservaServicio guardarReservaServicios(ReservaServicio reservaServicio) throws SQLException {
        ReservaServicio existente = reservaServicioRepository.findById(
            reservaServicio.getIdReserva(), reservaServicio.getIdServicio()
        );

        if (existente != null) {
            reservaServicioRepository.update(reservaServicio);
        } else {
            reservaServicioRepository.create(reservaServicio);
        }
        
        actualizarPrecioTotalDeReserva(reservaServicio.getIdReserva());

        return reservaServicio;
    }
    public ReservaServicio buscarReservaServicioPorId(int idReserva, int idServicio) throws SQLException{
        return reservaServicioRepository.findById(idReserva, idServicio);
    }

    public List<ReservaServicio> buscarReservaServiciosPorReserva(int idReserva) throws SQLException{
        return reservaServicioRepository.findByIdReserva(idReserva);
    }

    public List<ReservaServicio> buscarTodasLasReservaServicio() throws SQLException{
        return reservaServicioRepository.findAll();
    }

    public int eliminarReservaServicio(int idReserva, int idServicio) throws SQLException{
        return reservaServicioRepository.delete(idReserva, idServicio);
    }

    private void actualizarPrecioTotalDeReserva(int idReserva) throws SQLException {
    List<ReservaServicio> servicios = reservaServicioRepository.findByIdReserva(idReserva);
    double total = 0.0;

    for (ReservaServicio rs : servicios) {
        Servicio servicio = servicioService.buscarServicioPorId(rs.getIdServicio());
        total += servicio.getPrecio() * rs.getCantidad();
    }

    Reserva reserva = reservaService.buscarReservaPorId(idReserva);
    reserva.setPrecioFinal(total);
    reservaService.guardarReserva(reserva);
}

}
