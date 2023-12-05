package dev.patika.vetManagementSystem.dto.response.availableDateResponse;

import dev.patika.vetManagementSystem.dto.response.doctor.DoctorResponse2;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateResponse2 {
    private long id;
    private LocalDate date;
    private String doctorName;
}
