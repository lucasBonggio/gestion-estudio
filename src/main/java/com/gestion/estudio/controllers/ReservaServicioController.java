package com.gestion.estudio.controllers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gestion.estudio.entidades.Reserva;
import com.gestion.estudio.entidades.ReservaServicio;
import com.gestion.estudio.entidades.Servicio;
import com.gestion.estudio.services.ReservaService;
import com.gestion.estudio.services.ReservaServicioService;
import com.gestion.estudio.services.ServicioService;

@Controller
public class ReservaServicioController {

    private final ReservaServicioService reservaServicioService;
    private final ReservaService reservaService;
    private final ServicioService servicioService;

    public ReservaServicioController(
            ReservaServicioService reservaServicioService,
            ReservaService reservaService,
            ServicioService servicioService) {
        this.reservaServicioService = reservaServicioService;
        this.reservaService = reservaService;
        this.servicioService = servicioService;
    }

    @GetMapping("/reserva-servicios")
    public String listarReservaServicios(Model model) {
        try {
            List<ReservaServicio> lista = reservaServicioService.buscarTodasLasReservaServicio();
            model.addAttribute("reservaServicios", lista);
            model.addAttribute("reservaServicio", new ReservaServicio());
            model.addAttribute("reservas", reservaService.buscarTodasLasReservas());
            model.addAttribute("servicios", servicioService.buscarTodosLosServicios());
            model.addAttribute("mostrarModalCrearRS", true);

        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar reservas-servicio: " + e.getMessage());
        }
        return "reserva-servicios";
    }

    @GetMapping("/reserva-servicio/alta")
    public String altaReservaServicioForm(Model model) {
        try {
            // Cargar listas necesarias
            List<Reserva> reservas = reservaService.buscarTodasLasReservas();
            List<Servicio> servicios = servicioService.buscarTodosLosServicios();

            model.addAttribute("reservas", reservas);
            model.addAttribute("servicios", servicios);
            model.addAttribute("mostrarModalCrearRS", true);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar datos: " + e.getMessage());
        }

        return "reserva-servicios"; 
    }

    @PostMapping("/reserva-servicio/guardar")
    public String guardarReservaServicio(
            @RequestParam Integer idReserva,
            @RequestParam Integer idServicio,
            @RequestParam Integer cantidad,
            Model model) {

        try {
            // Crear la PK compuesta
            ReservaServicio  rs = new ReservaServicio();
            rs.setIdReserva(idReserva);
            rs.setIdServicio(idServicio);
            rs.setCantidad(cantidad);

            reservaServicioService.guardarReservaServicios(rs);

            return "redirect:/reserva-servicios";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al guardar: " + e.getMessage());
            return "redirect:/reserva-servicios";
        }
    }

    @GetMapping("/reserva-servicio/eliminar")
    public String eliminar(@RequestParam("idReserva") int idReserva,
                            @RequestParam("idServicio") int idServicio,
                            Model model) {
        try {
            reservaServicioService.eliminarReservaServicio(idReserva, idServicio);
        } catch (Exception e) {
            model.addAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "reserva-servicios";
    }

    @GetMapping("/reserva-servicio/editar")
    public String editar(@RequestParam("idReserva") int idReserva,
                        @RequestParam("idServicio") int idServicio,
                        Model model) throws SQLException {
        ReservaServicio rs = reservaServicioService.buscarReservaServicioPorId(idReserva, idServicio);
        model.addAttribute("reservaServicio", rs);
        model.addAttribute("reservas", reservaService.buscarTodasLasReservas());
        model.addAttribute("servicios", servicioService.buscarTodosLosServicios());
        return "formulario-reserva-servicio";
    }

    @GetMapping("/rs/buscar")
    public String buscarRS(@RequestParam(name="idReserva", required=false) Integer idReserva,
                            @RequestParam(name="idServicio", required=false) Integer idServicio,
                            Model model){
        List<ReservaServicio> resultado;
        try {
            if(idReserva != null && idServicio !=null){
                ReservaServicio rs = reservaServicioService.buscarReservaServicioPorId(idReserva, idServicio);
                resultado = (rs != null) ? List.of(rs) : Collections.emptyList();
                
            }else if(idServicio == null){
                resultado = reservaServicioService.buscarReservaServiciosPorReserva(idReserva);
            }else{
                model.addAttribute("error", "Criterio de búsqueda inválido. ");
                resultado = Collections.emptyList();
            }

            model.addAttribute("resultado", resultado);
            model.addAttribute("reservaServicios", reservaServicioService.buscarTodasLasReservaServicio());
            model.addAttribute("reservaServicio", new ReservaServicio());
            return "reserva-servicios";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al buscar las detalles: " + e.getMessage());
            return "reserva-servicios";
        }
    }
} 
