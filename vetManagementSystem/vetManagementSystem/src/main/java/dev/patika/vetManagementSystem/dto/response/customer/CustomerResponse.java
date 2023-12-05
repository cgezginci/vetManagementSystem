package dev.patika.vetManagementSystem.dto.response.customer;

import dev.patika.vetManagementSystem.entities.Animal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    private long id;
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;
    private List<Animal> animalList;

    public List<Animal> getAnimalList() {
        if (animalList == null) {
            return Collections.emptyList();
        }
        return animalList;
    }


}
