package dev.patika.vetManagementSystem.dto.response.doctor;

import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Appointment;
import dev.patika.vetManagementSystem.entities.AvailableDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {

    private long id;
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;
    private List<AvailableDate> availableDateList;
    private List<Appointment> appointmentList;

    public List<AvailableDate> getAvailableDateList() {
        if (availableDateList == null) {
            return Collections.emptyList();
        }
        return availableDateList;
    }

    public List<Appointment> getAppointmentList() {
        if (appointmentList == null) {
            return Collections.emptyList();
        }
        return appointmentList;
    }
}
