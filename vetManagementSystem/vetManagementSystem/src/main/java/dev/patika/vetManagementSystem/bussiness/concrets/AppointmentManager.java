package dev.patika.vetManagementSystem.bussiness.concrets;

import dev.patika.vetManagementSystem.bussiness.abstracts.IAppointmentService;
import dev.patika.vetManagementSystem.core.config.utilies.Msg;
import dev.patika.vetManagementSystem.core.exception.NotFoundException;
import dev.patika.vetManagementSystem.dao.AppointmentRepo;
import dev.patika.vetManagementSystem.entities.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentManager implements IAppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Override
    public Appointment save(Appointment appointment) {
        return this.appointmentRepo.save(appointment);
    }


    @Override
    public Appointment get(long id) {
        return this.appointmentRepo.findById(id).orElseThrow(() ->  new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public Appointment update(Appointment appointment) {
        return this.appointmentRepo.save(appointment);
    }

    @Override
    public boolean delete(long id) {
        try {
            Appointment appointment = this.get(id);
            this.appointmentRepo.delete(appointment);
            return true;
        } catch (NotFoundException ex) {
            return false;
        }

    }

    @Override
    public List<Appointment> getAppointmentsByDateAndAnimal(long animalId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        return appointmentRepo.findByDateBetweenAndAnimal_IdOrderByDate(startDateTime, endDateTime, animalId);
    }

}
