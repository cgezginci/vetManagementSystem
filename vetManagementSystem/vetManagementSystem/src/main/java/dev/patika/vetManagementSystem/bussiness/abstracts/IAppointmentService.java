package dev.patika.vetManagementSystem.bussiness.abstracts;

import dev.patika.vetManagementSystem.entities.Appointment;
import dev.patika.vetManagementSystem.entities.Vaccine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {

    Appointment save(Appointment appointment);
    Appointment get(long id);
    Appointment update(Appointment appointment);
    boolean delete(long id);

    List<Appointment> getAppointmentsByDateAndAnimal(long animalId, LocalDate startDate, LocalDate endDate);



}
