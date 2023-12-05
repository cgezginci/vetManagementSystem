package dev.patika.vetManagementSystem.dto.request.appointment;

import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveAppointmentRequest {

    private LocalDateTime date;
    private Doctor doctor;
    private Animal animal;


}
