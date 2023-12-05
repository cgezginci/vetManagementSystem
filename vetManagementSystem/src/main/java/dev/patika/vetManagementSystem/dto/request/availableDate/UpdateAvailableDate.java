package dev.patika.vetManagementSystem.dto.request.availableDate;

import dev.patika.vetManagementSystem.entities.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAvailableDate {

    private long id;
    private LocalDate date;
    private Doctor doctor;
}
