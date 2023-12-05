package dev.patika.vetManagementSystem.dto.request.animal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.patika.vetManagementSystem.entities.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAnimalRequest {


    private long id;

    private String name;


    private String species;


    private String breed;


    private String gender;


    private String color;


    private LocalDate dateOfBirth;


    private Customer customer;


}




