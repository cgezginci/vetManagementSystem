package dev.patika.vetManagementSystem.dto.response.animal;

import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalResponse2 {
    private long id;
    private String customerName;
    private String name;
    private String species;
    private String breed;
    private String gender;
    private LocalDate dateOfBirth;

    public static AnimalResponse2 AnimalAndCustomer(Animal animal, Customer customer) {
        AnimalResponse2 response = new AnimalResponse2();
        response.setId(animal.getId());
        response.setCustomerName(customer.getName());
        response.setName(animal.getName());
        response.setSpecies(animal.getSpecies());
        response.setBreed(animal.getBreed());
        response.setGender(animal.getGender());
        response.setDateOfBirth(animal.getDateOfBirth());

        return response;
    }


}
