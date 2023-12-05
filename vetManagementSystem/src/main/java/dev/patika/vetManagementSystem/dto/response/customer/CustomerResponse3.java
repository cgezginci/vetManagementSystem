package dev.patika.vetManagementSystem.dto.response.customer;

import dev.patika.vetManagementSystem.dto.response.animal.AnimalResponse2;
import dev.patika.vetManagementSystem.dto.response.animal.AnimalResponse3;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse3 {
    private long id;
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;
    private List<AnimalResponse3> animalList;


}
