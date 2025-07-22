package com.gestion.estudio.tests;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.gestion.estudio.entidades.Banda;
import com.gestion.estudio.entidades.Reserva;
import com.gestion.estudio.entidades.Sala;
import com.gestion.estudio.repositories.interfaces.I_BandaRepository;
import com.gestion.estudio.repositories.interfaces.I_ReservaRepository;
import com.gestion.estudio.repositories.interfaces.I_SalaRepository;

@SpringBootApplication(scanBasePackages="com.gestion.trabajo")
public class TestReservaRepository {
    
    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = SpringApplication.run(TestReservaRepository.class, args)) {
            I_SalaRepository salaDAO = context.getBean(I_SalaRepository.class);
            I_BandaRepository bandaDAO = context.getBean(I_BandaRepository.class);
            I_ReservaRepository reservaDAO = context.getBean(I_ReservaRepository.class);

            System.out.println("TEST 1: Creamos una reserva");
            
            Sala sala = salaDAO.findById(22);
            if(sala != null){
                System.out.println("Buscando sala por id: ");
                System.out.println("Encontrada existosamente: " + sala);
            }else{
                System.err.println("ERROR! No se pudo encontrar la sala. ");
            }

            Banda banda = bandaDAO.findById(21);
            if(banda != null){
                System.out.println("Buscando banda por id: ");
                System.out.println("Encontrada existosamente: " + banda);
            }else{
                System.err.println("ERROR! No se pudo encontrar la banda. ");
            }

            //Calculamos las horas
            LocalTime horaInicio = LocalTime.now();
            Duration duracion = Duration.between(horaInicio, horaInicio.plusHours(2));
            Long horas = duracion.toHours();

            Reserva reserva = new Reserva(22, banda.getId(), sala.getId(), LocalDate.now(), horaInicio, horaInicio.plusHours(2), horas * sala.getPrecioHora());
            reservaDAO.create(reserva);
            if(reserva.getId() > 0){
                System.out.println("Reserva creada correctamente. ID: " + reserva.getId());
                System.out.println("Reserva: " + reserva);
            }else{
                System.err.println("ERROR! No se pudo crear la reserva");
            }


            System.out.println("TEST 2: Buscar por id");
            Reserva reservaEncontrada  = reservaDAO.findById(reserva.getId());
            if(reservaEncontrada != null){
                System.out.println("Reserva encontrada. ID: " + reservaEncontrada.getId());
                System.out.println(reserva);
            }else{
                System.err.println("ERROR! No se pudo encontrar la reserva. ");
            }

            System.out.println("TEST 3: Buscar todas las reservas");
            
            List<Reserva> reservas = reservaDAO.findAll();
            
            if(!reservas.isEmpty()){
                for (Reserva res : reservas) {
                    System.out.println("Reserva " + res.getId() + ": " + res);
                    System.out.println("==================================");
                }
            }else{
                System.err.println("No hay reservas que mostrar. ");
            }

            System.out.println("TEST 4: Buscar reserva por banda");

            List<Reserva> reservasPorBanda = reservaDAO.findByBanda(8);
                        
            if(!reservasPorBanda.isEmpty()){
                for (Reserva res : reservasPorBanda) {
                    System.out.println("Reserva " + res.getId() + ": " + res);
                    System.out.println("==================================");
                }
            }else{
                System.err.println("No hay reservas de la banda. ");
            }

            System.out.println("TEST 5: Actualizar reserva");

            Reserva actualizar = new Reserva(22, 8, 22, LocalDate.now(), horaInicio, horaInicio, 2 * 32500);

            int filasAfectadas = reservaDAO.update(actualizar);
            if(filasAfectadas > 0){
                System.out.println("Reserva actualizada corretamente. ");
            }else{
                System.err.println("ERROR! No se pudo actualizar la reserva. ");
            }

            System.out.println("TEST 6: Eliminar reserva");
            
            int filasAfectadas2 = reservaDAO.delete(32);
            if(filasAfectadas2 > 0){
                System.out.println("Reserva eliminada corretamente. ");
            }else{
                System.err.println("ERROR! No se pudo eliminar la reserva. ");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
