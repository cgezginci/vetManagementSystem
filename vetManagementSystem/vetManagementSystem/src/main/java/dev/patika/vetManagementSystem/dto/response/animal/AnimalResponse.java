package dev.patika.vetManagementSystem.dto.response.animal;

import dev.patika.vetManagementSystem.dto.response.vaccine.VaccineResponse;
import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Appointment;
import dev.patika.vetManagementSystem.entities.Customer;
import dev.patika.vetManagementSystem.entities.Vaccine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalResponse {
    private long id;
    private String name;
    private String species;
    private String breed;
    private String gender;
    private LocalDate dateOfBirth;
    private String customerName;
    private List<Vaccine> vaccineList;
    private List<Appointment> appointmentList;

    public List<Vaccine> getVaccineList() {
        if (vaccineList == null) {
            return Collections.emptyList();
        }
        return vaccineList;
    }

    public List<Appointment> getAppointmentList() {
        if (appointmentList == null) {
            return Collections.emptyList();
        }
        return appointmentList;
    }

    public static AnimalResponse saveAnimalAndCustomer(Animal animal, Customer customer) {
        AnimalResponse response = new AnimalResponse();
        response.setId(animal.getId());
        response.setName(animal.getName());
        response.setSpecies(animal.getSpecies());
        response.setBreed(animal.getBreed());
        response.setGender(animal.getGender());
        response.setDateOfBirth(animal.getDateOfBirth());
        response.setCustomerName(customer.getName());
        response.setVaccineList(animal.getVaccineList());
        return response;
    }

    public static AnimalResponse saveAnimalAndVaccine(Animal animal, Vaccine vaccine) {
        AnimalResponse response = new AnimalResponse();
        response.setId(vaccine.getId());
        response.setName(vaccine.getName());
        response.setSpecies(animal.getSpecies());
        response.setBreed(animal.getBreed());
        response.setGender(animal.getGender());
        response.setDateOfBirth(animal.getDateOfBirth());
        response.setCustomerName(animal.getCustomer().getName());
        response.setVaccineList(animal.getVaccineList());

        return response;
    }



}
