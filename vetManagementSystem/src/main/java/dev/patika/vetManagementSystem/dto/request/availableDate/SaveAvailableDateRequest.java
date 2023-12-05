package dev.patika.vetManagementSystem.dto.request.availableDate;

import dev.patika.vetManagementSystem.entities.Customer;
import dev.patika.vetManagementSystem.entities.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.print.Doc;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveAvailableDateRequest {

    private LocalDate date;
    private Doctor doctor;


}
