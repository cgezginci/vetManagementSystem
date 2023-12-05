package dev.patika.vetManagementSystem.dao;

import dev.patika.vetManagementSystem.entities.Appointment;
import dev.patika.vetManagementSystem.entities.AvailableDate;
import dev.patika.vetManagementSystem.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDateBetweenAndAnimal_IdOrderByDate(LocalDateTime startDate, LocalDateTime endDate, long animalId);

    List<Appointment> findByDateBetweenAndDoctor_IdOrderByDate(LocalDateTime startDate, LocalDateTime endDate, long doctorId);




}
