package dev.patika.vetManagementSystem.dto.request.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDoctorRequest {
    private long id;
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;
}
