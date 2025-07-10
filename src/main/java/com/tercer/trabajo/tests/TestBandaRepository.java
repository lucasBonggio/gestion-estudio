package com.tercer.trabajo.tests;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.tercer.trabajo.entidades.Banda;
import com.tercer.trabajo.repositories.interfaces.I_BandaRepository;

@SpringBootApplication(scanBasePackages="com.tercer.trabajo")
public class TestBandaRepository {
    public static void main(String[] args) {
        try (
            ConfigurableApplicationContext context = SpringApplication.run(TestBandaRepository.class, args)) {
            I_BandaRepository bandaDAO = context.getBean(I_BandaRepository.class);

            System.out.println("TEST 1: Creamos una banda");
            Banda banda = new Banda(99, "Metallica", "Heavy-metal", 5, "metallica@gmail.com", null);
            bandaDAO.create(banda);
            if(banda.getId() > 0){
                System.out.println("Banda creada exitosamente. ID: " + banda.getId());
                System.out.println(banda);
            }else{
                System.err.println("ERROR! No se pudo crear la banda");
            }

            System.out.println("TEST 2: Buscamos por id");
            Banda bandaEncontrada = bandaDAO.findById(19);
            if(bandaEncontrada != null){
                System.out.println("Banda encontrada. ");
                System.out.println("Banda: " + bandaEncontrada);
            }else{
                System.err.println("ERROR! No se pudo encontrar la banda. ");
            }

            System.out.println("TEST 3: Buscar todas las bandas");
            List<Banda> bandas = bandaDAO.findAll();
            for (Banda band : bandas) {
                System.out.println("Banda " + band.getId() + ": " + band);
            }

            System.out.println("TEST 4: Buscar por reserva");
            Banda bandaReserva = bandaDAO.findByReserva(8);
            if(bandaReserva != null){
                System.out.println("Banda encontrada. ");
                System.out.println("Banda: " + bandaReserva);
            }else{
                System.err.println("ERROR! No se pudo encontrar la banda. ");
            }


            System.out.println("TEST 5: Actualizamos una banda");

            bandaEncontrada.setCantidadMusicos(4);

            int filasAfectadas = bandaDAO.update(bandaEncontrada, 19);
            if(filasAfectadas > 0){
                System.out.println("Banda actualizada corretamente. ");
            }else{
                System.err.println("ERROR! No se pudo actualizar la banda. ");
            }

            System.out.println("TEST 6: Borrar banda");
            int filasAfectadas2 = bandaDAO.delete(banda.getId());
            if(filasAfectadas2 > 0){
                System.out.println("Banda eliminada corretamente. ");
            }else{
                System.err.println("ERROR! No se pudo eliminar la banda. ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
