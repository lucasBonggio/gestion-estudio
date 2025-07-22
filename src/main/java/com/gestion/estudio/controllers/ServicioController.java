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

import com.gestion.estudio.entidades.Servicio;
import com.gestion.estudio.services.ServicioService;

@Controller
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping("/servicios")
    public String listarServicios(Model model) {
        try {
            List<Servicio> servicios = servicioService.buscarTodosLosServicios();
            model.addAttribute("servicios", servicios);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar los servicios: " + e.getMessage());
        }
        return "servicios";
    }

    @GetMapping("/servicio/alta")
    public String altaServicioForm(Model model) {
        model.addAttribute("servicio", new Servicio());
        return "formulario-servicio";
    }

    @PostMapping("/servicio/guardar")
    public String guardarServicio(@ModelAttribute("servicio") Servicio servicio,
                                @RequestParam(name="id", required=false, defaultValue="0") String id,
                                Model model) {
        try {
            Servicio servicioToSave = new Servicio();
            servicioToSave.setNombre(servicio.getNombre());
            servicioToSave.setPrecio(servicio.getPrecio());
            
            try {
                int idValue = Integer.parseInt(id);
                servicioToSave.setId(idValue);
            } catch (Exception e) {
                servicioToSave.setId(0);
            }
            servicioService.guardarServicio(servicio);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al guardar el servicio: " + e.getMessage());
        }
        return "redirect:/servicios";
    }

    @GetMapping("/servicio/editar")
    public String editarServicioForm(@RequestParam("id") Integer id,
                                    @RequestParam("modo") String modo,
                                    Model model) {
        try {
            List<Servicio> servicios = servicioService.buscarTodosLosServicios();
            model.addAttribute("servicios", servicios);

            if ("editar".equals(modo) && id != null) {
                Servicio servicio = servicioService.buscarServicioPorId(id);
                if(servicio != null){
                    model.addAttribute("servicio", servicio);
                    model.addAttribute("servicio", new Servicio());
                }
                
            } else {
                model.addAttribute("servicio", new Servicio());
            }

            return "servicios";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar el servicio: " + e.getMessage());
            return "redirect:/servicios";
        }
    }

    @PostMapping("/servicio/actualizar")
    public String actualizarServicio(@ModelAttribute("servicio") Servicio servicio, Model model) {
        try {
            servicioService.guardarServicio(servicio);
            return "redirect:/servicios";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al actualizar el servicio: " + e.getMessage());
            return "formulario-servicio";
        }
    }

    @GetMapping("/servicio/eliminar")
    public String eliminarServicio(@RequestParam("id") int id, Model model) {
        try {
            servicioService.eliminarServicio(id);
            return "redirect:/servicios";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al eliminar el servicio: " + e.getMessage());
            try {
                model.addAttribute("servicios", servicioService.buscarTodosLosServicios());
            } catch (SQLException ex) {
                ex.printStackTrace();
                model.addAttribute("error", "Error al recargar los servicios: " + ex.getMessage());
            }
            return "servicios";
        }
    }


    @GetMapping("/servicios/buscar")
    public String buscarServicio(@RequestParam("criterio") String criterio,
                                @RequestParam("valor") String valor,
                                Model model) throws SQLException{
        try {
            List<Servicio> resultado;            
            if ("id".equalsIgnoreCase(criterio)) {
                try {
                    int id = Integer.parseInt(valor);
                    Servicio servicio = servicioService.buscarServicioPorId(id);
                    resultado = (servicio != null) ? List.of(servicio) : Collections.emptyList();
                } catch (NumberFormatException e) {
                    model.addAttribute("error", "ID inválido. ");
                    resultado = Collections.emptyList();
                }
            } else if("nombre".equalsIgnoreCase(criterio)) {
                Servicio servicio = servicioService.buscarServicioPorNombre(valor);
                resultado = (servicio != null) ? List.of(servicio) : Collections.emptyList();
            }else{
                model.addAttribute("error", "Criterio de búsqueda inválido. ");
                resultado = Collections.emptyList();
            }

            model.addAttribute("resultado", resultado);
            model.addAttribute("servicios", servicioService.buscarTodosLosServicios());
            model.addAttribute("servicio", new Servicio());
            return "servicios";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al buscar el servicio: " + e.getMessage());
            return "servicios";
        }
    }
}
