package dev.patika.vetManagementSystem.dto.response.availableDateResponse;


import dev.patika.vetManagementSystem.entities.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateResponse {

    private long id;
    private LocalDate date;
    private Doctor doctor;



}
