package com.tercer.trabajo.tests;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.tercer.trabajo.entidades.ReservaServicio;
import com.tercer.trabajo.repositories.interfaces.I_ReservaServicioRepository;

@SpringBootApplication(scanBasePackages="com.tercer.trabajo")
public class TestReservaServicioRepository {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = SpringApplication.run(TestReservaServicioRepository.class, args)) {
            I_ReservaServicioRepository reservaServicioDAO = context.getBean(I_ReservaServicioRepository.class);

            System.out.println("TEST 1: Crear una reservaServicio");

            ReservaServicio reservaServicio = new ReservaServicio(4, 16, 2);
            reservaServicioDAO.create(reservaServicio);

            System.out.println("TEST 2: Buscar por id");

            //Buscamos el rs creado anteriormente para chequear si se creó bien.
            ReservaServicio reservaServicioEncontrado = reservaServicioDAO.findById(8, 1);

            if(reservaServicioEncontrado != null){
                System.out.println("La reserva del servicio se encontró exitosamente. ");
                System.out.println(reservaServicioEncontrado);
            }else{
                System.err.println("ERROR! No se pudo encontrar la reserva del servicio");
            }
            
            System.out.println("TEST 3: Buscar todas las reservas de servicios");

            List<ReservaServicio> reservaServicios = reservaServicioDAO.findAll();

            if(!reservaServicios.isEmpty()){
                for (ReservaServicio rs : reservaServicios) {
                    System.out.println("Reserva del servicio: " + rs);
                }
            }else{
                System.err.println("No hay reserva de servicios que mostrar. ");
            }

            System.out.println("TEST 4: Buscar por reserva");

            List<ReservaServicio> serviciosAdquiridos = reservaServicioDAO.findByReserva(8);
            if(!serviciosAdquiridos.isEmpty()){
                for (ReservaServicio rs : serviciosAdquiridos) {
                    System.out.println("Servicio de la reserva: " + rs);
                }
            }else{
                System.err.println("No hay reserva de servicios en la reserva. ");
            }

            System.out.println("TEST 5: Actualizar reserva del servicio");

            ReservaServicio rsActualizar = new ReservaServicio(8, 11, 4);
            
            int filasAfectadas = reservaServicioDAO.update(rsActualizar);
            if(filasAfectadas > 0){
                System.out.println("Reserva del servicio actualizado exitosamente");
            }else{
                System.err.println("ERROR! No se pudo actualizar la reserva del servicio");
            }

            
            // System.out.println("TEST 6: Eliminar la reserva de un servicio");

            // int filasAfectadas2 = reservaServicioDAO.delete(8, 3);
            // if(filasAfectadas2 > 0){
            //     System.out.println("Reserva del servicio se eliminó exitosamente");
            // }else{
            //     System.err.println("ERROR! No se pudo eliminar la reserva del servicio");
            // }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
