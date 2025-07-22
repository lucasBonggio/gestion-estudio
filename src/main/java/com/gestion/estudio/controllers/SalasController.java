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

import com.gestion.estudio.entidades.Sala;
import com.gestion.estudio.enums.Tipo;
import com.gestion.estudio.services.BandaService;
import com.gestion.estudio.services.ReservaService;
import com.gestion.estudio.services.SalaService;
import com.gestion.estudio.services.ServicioService;

@Controller
public class SalasController {
    private final SalaService salaService;
    private final BandaService bandaService;
    private final ServicioService servicioService;
    private final ReservaService reservaService;
    
    public SalasController(SalaService salaService, BandaService bandaService, ServicioService servicioService, ReservaService reservaService){
        this.salaService = salaService;
        this.bandaService = bandaService;
        this.servicioService = servicioService;
        this.reservaService = reservaService;
    }

    @GetMapping("/")
    public String index(Model model) throws SQLException {
        model.addAttribute("tipos", Tipo.values());
        model.addAttribute("salas", salaService.buscarTodasLasSalas());
        model.addAttribute("bandas", bandaService.buscarTodasLasBandas());
        model.addAttribute("reservas", reservaService.buscarTodasLasReservas());
        model.addAttribute("servicios", servicioService.buscarTodosLosServicios());
        model.addAttribute("mostrarModalCrearRS", true);
        return "index";
    }

    @GetMapping("/salas")
    public String listarSalas(Model model){
        try {
            List<Sala> salas = salaService.buscarTodasLasSalas();
            model.addAttribute("salas", salas);
            model.addAttribute("tipos", Tipo.values());
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar las salas: " + e.getMessage());
        }
        return "salas";
    }

    @GetMapping("/sala/alta")
    public String altaSalaForm(Model model){
        model.addAttribute("sala", new Sala());
        model.addAttribute("tipos", Tipo.values());
        return "formulario-sala";
    }

    @PostMapping("/sala/guardar")
        public String guardarSala(
                @ModelAttribute("sala") Sala sala,
                @RequestParam(name = "id", required = false, defaultValue = "0") String id,
                Model model
        ) {
            try {
                Sala salaToSave = new Sala();
                salaToSave.setNombre(sala.getNombre());
                salaToSave.setTipo(sala.getTipo());
                salaToSave.setPrecioHora(sala.getPrecioHora());
                salaToSave.setCapacidad(sala.getCapacidad());

                try {
                    int idValue = Integer.parseInt(id);
                    salaToSave.setId(idValue); // Edición o creación con id=0
                } catch (NumberFormatException e) {
                    salaToSave.setId(0); // Creación: id inválido o vacío, usamos 0
                }

                salaService.guardarSala(salaToSave);
                model.addAttribute("mensaje", "Sala guardada con éxito");
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("error", "Error al guardar la sala: " + e.getMessage());
            }
            return "redirect:/salas";
        }

    @GetMapping("/salas/editar")
    public String actualizarSala(
        @RequestParam(value = "id", required = false) Integer id,
        @RequestParam(value = "modo", required = false) String modo,
        Model model
    ) {
        try {
            List<Sala> salas = salaService.buscarTodasLasSalas();
            model.addAttribute("salas", salas);

            if ("editar".equals(modo) && id != null) {
                Sala salaEditar = salaService.buscarSalaPorId(id);
                if (salaEditar != null) {
                    model.addAttribute("sala", salaEditar);
                    model.addAttribute("tipos", Tipo.values());
                    model.addAttribute("sala", new Sala());
                }
            } else {
                model.addAttribute("sala", new Sala());
                model.addAttribute("tipos", Tipo.values());
            }

            return "salas"; 
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar salas: " + e.getMessage());
            return "salas";
        }
    }

    @GetMapping("/sala/eliminar")
    public String eliminarSala(@RequestParam("id") int id, Model model){
        try {
            salaService.eliminarSala(id);
            return "redirect:/salas";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al eliminar la sala: " + e.getMessage());
            try {
                model.addAttribute("salas", salaService.buscarTodasLasSalas());
            } catch (SQLException ex) {
                ex.printStackTrace();
                model.addAttribute("error", "Error al recargar las salas luego de eliminar: " + ex.getMessage());
            }
            return "salas";
        }
    }

    @GetMapping("/salas/buscar")
        public String buscarSala(
                @RequestParam("criterio") String criterio,
                @RequestParam("valor") String valor,
                Model model
        ) throws SQLException {
            try {
                List<Sala> resultado;
                if ("id".equalsIgnoreCase(criterio)) {
                    try {
                        int id = Integer.parseInt(valor);
                        Sala sala = salaService.buscarSalaPorId(id);
                        resultado = (sala != null) ? List.of(sala) : Collections.emptyList();
                    } catch (NumberFormatException e) {
                        model.addAttribute("error", "ID inválido. Por favor, ingrese un número válido.");
                        resultado = Collections.emptyList();
                    }
                } else if ("nombre".equalsIgnoreCase(criterio)) {
                    Sala sala = salaService.buscarSalaPorNombre(valor);
                    resultado = (sala != null) ? List.of(sala) : Collections.emptyList();
                } else {
                    model.addAttribute("error", "Criterio de búsqueda no válido.");
                    resultado = Collections.emptyList();
                }
                model.addAttribute("resultado", resultado);
                model.addAttribute("salas", salaService.buscarTodasLasSalas());
                model.addAttribute("tipos", Tipo.values());
                model.addAttribute("sala", new Sala());
                return "salas";
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("error", "Error al buscar salas: " + e.getMessage());
                model.addAttribute("resultado", Collections.emptyList());
                model.addAttribute("salas", salaService.buscarTodasLasSalas());
                model.addAttribute("tipos", Tipo.values());
                model.addAttribute("sala", new Sala());
                return "salas";
            }
        }
}