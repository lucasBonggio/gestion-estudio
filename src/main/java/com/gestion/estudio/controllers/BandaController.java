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

import com.gestion.estudio.entidades.Banda;
import com.gestion.estudio.services.BandaService;

@Controller
public class BandaController {

    private final BandaService bandaService;

    public BandaController(BandaService bandaService){
        this.bandaService = bandaService;
    }

    @GetMapping("/bandas")
    public String listarBandas(Model model){
        try {
            List<Banda> bandas = bandaService.buscarTodasLasBandas();
            model.addAttribute("bandas", bandas);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar las bandas: " + e.getMessage());
        }
        return "bandas";
    }

    @GetMapping("/banda/alta")
    public String altaBandaForm(Model model){
        model.addAttribute("banda", new Banda());
        return "formulario-banda";
    }

    @PostMapping("/banda/guardar")
    public String guardarBanda(@ModelAttribute("banda") Banda banda,
                                @RequestParam(name="id", required=false, defaultValue="0") String id,
                                Model model){
        try {
            Banda bandaToSave = new Banda();
            bandaToSave.setNombre(banda.getNombre());
            bandaToSave.setGenero(banda.getGenero());
            bandaToSave.setCantidadMusicos(banda.getCantidadMusicos());
            bandaToSave.setContacto(banda.getContacto());
            if(banda.getObservaciones() != null){
                bandaToSave.setObservaciones(banda.getObservaciones());
            }

            try {
                int idValue = Integer.parseInt(id);
                bandaToSave.setId(idValue);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                model.addAttribute("error", "Error al guardar la banda: " + e.getMessage());
            }

            bandaService.guardarBanda(banda);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al guardar la banda: "+ e.getMessage());
        }
        return "redirect:/bandas";
    }

    @GetMapping("/banda/editar")
    public String editarBandaForm(@RequestParam("id") Integer id,
                                @RequestParam("modo") String modo,
                                Model model){
        try {
            List<Banda> bandas = bandaService.buscarTodasLasBandas();
            model.addAttribute("bandas", bandas);


            if("editar".equals(modo) && id != null){
                Banda banda = bandaService.buscarBandaPorId(id);
                if(banda != null){
                    model.addAttribute("banda", banda);
                    model.addAttribute("banda", new Banda());
                }
            }else{
                model.addAttribute("banda", new Banda());
            }
            return "bandas";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar la banda para editar: " + e.getMessage());
            return "redirect:/bandas";
        }
    }

    @PostMapping("banda/actualizar")
    public String actualizarBanda(@ModelAttribute("banda") Banda banda, Model model){
        try {
            bandaService.guardarBanda(banda);
            return "redirect:/bandas";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al actualizar la banda: " + e.getMessage());
            return "formulario-banda";
        }
    }

    @GetMapping("/banda/eliminar")
    public String eliminarBanda(@RequestParam("id") int id, Model model){
        try {
            bandaService.eliminarBanda(id);
            return "redirect:/bandas";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al eliminar la banda: " + e.getMessage());
            try {
                model.addAttribute("bandas", bandaService.buscarTodasLasBandas());
            } catch (SQLException ex) {
                ex.printStackTrace();
                model.addAttribute("error", "Error al recargar las bandas luego de la eliminación: " + ex.getMessage());
            }
            return "bandas";
        }
    }

    @GetMapping("/bandas/buscar")
    public String buscarBanda(@RequestParam("criterio") String criterio,
                                        @RequestParam("valor") String valor,
                                        Model model){
        try {
            List<Banda> resultado;
            if("id".equalsIgnoreCase(criterio)){
                try {
                    int id = Integer.parseInt(valor);
                    Banda banda = bandaService.buscarBandaPorId(id);
                    resultado = (banda != null) ? List.of(banda) : Collections.emptyList();
                } catch (NumberFormatException e) {
                    model.addAttribute("error", "ID inválido. ");
                    resultado = Collections.emptyList();
                }
            }else if("nombre".equalsIgnoreCase(criterio)){
                Banda banda = bandaService.buscarBandaPorNombre(valor);
                resultado = (banda != null) ? List.of(banda) : Collections.emptyList();
            }else{
                model.addAttribute("error", "Criterio de búsqueda inválido. ");
                resultado = Collections.emptyList();
            }

            model.addAttribute("resultado", resultado);
            model.addAttribute("bandas", bandaService.buscarTodasLasBandas());
            model.addAttribute("banda", new Banda());
            return "bandas";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al buscar la banda: " + e.getMessage());
            return "bandas";
        }
    }
}
