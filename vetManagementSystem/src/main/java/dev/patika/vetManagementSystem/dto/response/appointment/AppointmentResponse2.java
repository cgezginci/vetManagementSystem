package dev.patika.vetManagementSystem.dto.response.appointment;

import dev.patika.vetManagementSystem.entities.Appointment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse2 {
    private long id;
    private LocalDateTime date;
    private String doctorName;
    private String animalName;

    public static AppointmentResponse2 fromAppointmentAndDoctorAndAnimal(Appointment appointment) {
        return new AppointmentResponse2(
                appointment.getId(),
                appointment.getDate(),
                appointment.getDoctor().getName(),
                appointment.getAnimal().getName()
        );
    }

}
