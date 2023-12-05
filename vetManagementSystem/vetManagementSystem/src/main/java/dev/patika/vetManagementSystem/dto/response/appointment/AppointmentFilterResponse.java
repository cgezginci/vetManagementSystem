package dev.patika.vetManagementSystem.dto.response.appointment;


import dev.patika.vetManagementSystem.entities.Animal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentFilterResponse {
        private long id;
        private LocalDateTime date;
        private Animal animal ;
}
