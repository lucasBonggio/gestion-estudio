package com.gestion.estudio.controllers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gestion.estudio.entidades.Reserva;
import com.gestion.estudio.services.BandaService;
import com.gestion.estudio.services.ReservaService;
import com.gestion.estudio.services.SalaService;

@Controller
public class ReservaController {

    private final ReservaService reservaService;
    private final SalaService salaService;
    private final BandaService bandaService;

    public ReservaController(ReservaService reservaService, SalaService salaService, BandaService bandaService){
        this.reservaService = reservaService;
        this.salaService = salaService;
        this.bandaService = bandaService;
    }


    @GetMapping("/reservas")
    public String listarReservas(Model model){
        try {
            List<Reserva> reservas = reservaService.buscarTodasLasReservas();
            model.addAttribute("reservas", reservas);
            model.addAttribute("salas", salaService.buscarTodasLasSalas());
            model.addAttribute("bandas", bandaService.buscarTodasLasBandas());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar las reservas: " + e.getMessage());
        }
        return "reservas";
    }

    @GetMapping("/reserva/alta")
    public String altaReservaForm(Model model) throws SQLException{
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("bandas", bandaService.buscarTodasLasBandas());
        model.addAttribute("salas", salaService.buscarTodasLasSalas());
        return "formulario-reserva";
    }

    @PostMapping("/reserva/guardar")
    public String guardarReserva(@ModelAttribute("reserva") Reserva reserva,
                                @RequestParam(name = "id", required = false, defaultValue="0") String id,
                                Model model){
        try {
            Reserva reservaToSave = new Reserva();
            reservaToSave.setIdBanda(reserva.getIdBanda());
            reservaToSave.setIdSala(reserva.getIdSala());
            reservaToSave.setFecha(reserva.getFecha());
            reservaToSave.setHoraInicio(reserva.getHoraInicio());
            reservaToSave.setHoraFin(reserva.getHoraFin());
            reservaToSave.setPrecioFinal(reserva.getPrecioFinal());

            try {
                int idValue = Integer.parseInt(id);
                reservaToSave.setId(idValue);
            } catch (NumberFormatException e) {
                reservaToSave.setId(0);
            }

            model.addAttribute("bandas", bandaService.buscarTodasLasBandas());
            model.addAttribute("salas", salaService.buscarTodasLasSalas());
            reservaService.guardarReserva(reservaToSave);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al guardar la reserva: " + e.getMessage());
        }
        return "redirect:/reservas";
    }

    @GetMapping("/reserva/editar")
    public String editarBandaForm(@RequestParam(name = "id") Integer id,
                                @RequestParam(name = "modo") String modo,
                                Model model){
        try {
            List<Reserva> reservas = reservaService.buscarTodasLasReservas();
            model.addAttribute("reservas", reservas);

            if("editar".equalsIgnoreCase(modo) && id != null){
                Reserva reservaEditar = reservaService.buscarReservaPorId(id);
                if(reservaEditar != null){
                    model.addAttribute("reserva", reservaEditar);
                    model.addAttribute("reserva", new Reserva());
                }
            }else{
                model.addAttribute("reserva", new Reserva());
                model.addAttribute("bandas", bandaService.buscarTodasLasBandas());
                model.addAttribute("salas", salaService.buscarTodasLasSalas());
            }
            Reserva reserva = reservaService.buscarReservaPorId(id);
            if(reserva != null){
                model.addAttribute("reserva", reserva);
                return "formulario-banda";
            }else{
                return "redirect:/reservas";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar la reserva para editar: " + e.getMessage());
            return "redirect:/reservas";
        }
    }

    @PostMapping("reserva/actualizar")
    public String actualizarBanda(@ModelAttribute("reserva") Reserva reserva, Model model){
        try {
            reservaService.guardarReserva(reserva);
            return "redirect:/reservas";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al actualizar la banda: " + e.getMessage());
            return "formulario-reserva";
        }
    }

    @GetMapping("/reserva/eliminar")
    public String eliminarReserva(@RequestParam("id") int id, Model model){
        try{
            reservaService.eliminarReserva(id);
            return "redirect:/reservas";
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("error", "Error al eliminar la reserva: " + e.getMessage());
            try{
                model.addAttribute("reservas", reservaService.buscarTodasLasReservas());
            }catch(SQLException ex){
                ex.printStackTrace();
                model.addAttribute("error", "Error al recargar las reservas luego de eliminar: " + ex.getMessage());
            }
            return "reservas";
        }
    }

    @GetMapping("/reservas/buscar")
    public String buscarReservaPorBanda(@RequestParam("criterio") String criterio,
                                        @RequestParam("valor") String valor,
                                        Model model){
        try {
            List<Reserva> resultado;
            if ("id".equalsIgnoreCase(criterio)) {
                try {
                    int id = Integer.parseInt(valor);
                    Reserva reserva = reservaService.buscarReservaPorId(id);
                    resultado = (reserva != null) ? List.of(reserva) : Collections.emptyList();
                } catch (NumberFormatException e) {
                    model.addAttribute("error", "ID inválido. ");
                    resultado = Collections.emptyList();
                }
            } else if("banda".equalsIgnoreCase(criterio)){
                int idBanda = Integer.parseInt(valor);
                resultado = reservaService.buscarReservasPorBanda(idBanda);
                if (resultado == null) {
                    resultado = Collections.emptyList();
                }
            }else{
                model.addAttribute("error", "Criterio de búsqueda inválido. ");
                resultado = Collections.emptyList();
            }
            model.addAttribute("resultado", resultado);
            model.addAttribute("reservas", reservaService.buscarTodasLasReservas());
            model.addAttribute("reserva", new Reserva());

            return "reservas";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al buscar reservas: " + e.getMessage());
            return "reservas";
        }
    }

}   

