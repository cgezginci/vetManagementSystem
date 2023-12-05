package dev.patika.vetManagementSystem.dto.request.animal;

import dev.patika.vetManagementSystem.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveAnimalRequest {


    private String name;
    private String species;
    private String breed;
    private String gender;
    private String color;
    private LocalDate dateOfBirth;
    private Customer customer;

    public Customer getCustomer() {
        // Eğer customer null ise, yeni bir Customer nesnesi oluştur ve döndür
        if (customer == null) {
            return new Customer();
        }
        return customer;
    }



}
