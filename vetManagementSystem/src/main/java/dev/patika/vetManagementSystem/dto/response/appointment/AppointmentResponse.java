package dev.patika.vetManagementSystem.dto.response.appointment;

import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {

    private long id;
    private LocalDateTime date;
    private Doctor doctor;
    private Animal animal;


}
