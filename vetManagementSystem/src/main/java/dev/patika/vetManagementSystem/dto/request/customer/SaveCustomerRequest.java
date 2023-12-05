package dev.patika.vetManagementSystem.dto.request.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveCustomerRequest {

    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;

}
