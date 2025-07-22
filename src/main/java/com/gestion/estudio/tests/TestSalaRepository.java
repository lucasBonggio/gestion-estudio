package com.gestion.estudio.tests;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.gestion.estudio.entidades.Sala;
import com.gestion.estudio.enums.Tipo;
import com.gestion.estudio.repositories.interfaces.I_SalaRepository;

@SpringBootApplication(scanBasePackages="com.gestion.trabajo")
public class TestSalaRepository {

    public static void main(String[] args) {
        try (
            ConfigurableApplicationContext context = SpringApplication.run(TestSalaRepository.class, args);) {
            I_SalaRepository salaDAO = context.getBean(I_SalaRepository.class);
            
            System.out.println("TEST 1: Crear una sala");

            Sala sala = new Sala(99, "Sala 99", 8, Tipo.Ensayo, 506000);
            salaDAO.create(sala);

            if(sala.getId() > 0){
                System.out.println("Sala creada existosamente. ID: " + sala.getId());
                System.out.println(sala);
            }else{
                System.err.println("ERROR! No se pudo crear la sala. ");
            }


            System.out.println("TEST 2: Buscar por id");

            Sala salaEncontrada = salaDAO.findById(sala.getId());
            if(salaEncontrada != null){
                System.out.println("Buscando sala por id: ");
                System.out.println("Encontrada existosamente: " + salaEncontrada);
            }else{
                System.err.println("ERROR! No se pudo encontrar la sala. ");
            }


            System.out.println("TEST 3: Buscar sala por nombre");

            Sala salaPorReserva = salaDAO.findByNombre("Sala 1");
            if(salaPorReserva != null){
                System.out.println("Sala encontrada exitosamente");
                System.out.println(salaPorReserva);
            }else{
                System.err.println("ERROR! No se pudo encontrar la sala");
            }


            System.out.println("TEST 4: Buscando todas las salas");

            List<Sala> salas = salaDAO.findAll();
            if(!salas.isEmpty()){
                for (Sala sa : salas) {
                System.out.println("Sala " + sa.getId() + ": " + sa);
                }
            }else {
                System.err.println("No hay salas que mostrar. ");
            }

            System.out.println("TEST 5: Actualizar sala");

            Sala salaActualizada = new Sala();
            salaActualizada.setId(2);
            salaActualizada.setNombre("Sala Dimebag");
            salaActualizada.setCapacidad(5);
            salaActualizada.setTipo(Tipo.Ensayo);
            salaActualizada.setPrecioHora(450500);

            int filasAfectadas2 = salaDAO.update(salaActualizada);
            if(filasAfectadas2 > 0){
                System.out.println("La sala se actualizó correctamente. ");
            }else{
                System.err.println("ERROR! No se pudo actualizar la sala. ");
            }

            System.out.println("TEST 6: Borrar una sala: ");

            int filasAfectadas = salaDAO.delete(35);
            if(filasAfectadas > 0){
                System.out.println("La sala se eliminó correctamente. ");
            }else{
                System.err.println("ERROR! No se pudo eliminar la sala. ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
}
