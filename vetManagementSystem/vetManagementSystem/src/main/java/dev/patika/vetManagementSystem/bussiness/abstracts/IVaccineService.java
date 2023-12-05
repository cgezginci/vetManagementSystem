package dev.patika.vetManagementSystem.bussiness.abstracts;

import dev.patika.vetManagementSystem.dto.request.vaccine.SaveVaccineRequest;
import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Customer;
import dev.patika.vetManagementSystem.entities.Vaccine;

import java.time.LocalDate;
import java.util.List;

public interface IVaccineService {

    Vaccine save(Vaccine vaccine);

    Vaccine get(long id);

    Vaccine update(Vaccine vaccine);

    boolean delete (long id);

    Vaccine getVaccineByName(String name);
    List<Vaccine> getVaccinesByName(String vaccineName);



    public List<Vaccine> getVaccinesByDate(LocalDate protectionStartDate, LocalDate protectionEndDate);


    List<Vaccine> findByName(String name);
}
