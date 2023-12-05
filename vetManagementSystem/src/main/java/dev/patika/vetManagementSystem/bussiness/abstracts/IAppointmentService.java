package dev.patika.vetManagementSystem.bussiness.abstracts;

import dev.patika.vetManagementSystem.core.result.ListResult;
import dev.patika.vetManagementSystem.core.result.ResultData;
import dev.patika.vetManagementSystem.dto.request.appointment.SaveAppointmentRequest;
import dev.patika.vetManagementSystem.dto.request.appointment.UpdateAppointmentRequest;
import dev.patika.vetManagementSystem.dto.response.appointment.AppointmentResponse;
import dev.patika.vetManagementSystem.dto.response.appointment.AppointmentResponse2;
import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Appointment;
import dev.patika.vetManagementSystem.entities.AvailableDate;
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

    ResultData<AppointmentResponse2> save2(SaveAppointmentRequest saveAppointmentRequest);


    ResultData<AppointmentResponse2> get2(long id);

    ResultData<AppointmentResponse2> update2(UpdateAppointmentRequest updateAppointmentRequest);

    List<AppointmentResponse2> getAppointmentsByAnimalNameAndDateRange(String animalName, LocalDate startDate, LocalDate endDate);

    List<AppointmentResponse2> getAppointmentsByDoctorNameAndDateRange(String doctorName, LocalDate startDate, LocalDate endDate);


}
