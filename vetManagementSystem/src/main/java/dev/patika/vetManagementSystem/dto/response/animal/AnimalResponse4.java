package dev.patika.vetManagementSystem.dto.response.animal;

import dev.patika.vetManagementSystem.entities.Vaccine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalResponse4 {
    private List<AnimalResponse2> animalList;
    private List<Vaccine> vaccineList;
}
