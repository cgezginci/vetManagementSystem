package dev.patika.vetManagementSystem.bussiness.abstracts;

import dev.patika.vetManagementSystem.core.result.ListResult;
import dev.patika.vetManagementSystem.core.result.ResultData;
import dev.patika.vetManagementSystem.dto.request.vaccine.SaveVaccineRequest;
import dev.patika.vetManagementSystem.dto.request.vaccine.UpdateVaccineRequest;
import dev.patika.vetManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.vetManagementSystem.dto.response.animal.AnimalResponse4;
import dev.patika.vetManagementSystem.dto.response.vaccine.VaccineResponse;
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

    List<Animal> getAnimalsByVaccineAndAnimalNames(String vaccineName, String animalName);



    public List<Vaccine> getVaccinesByDate(LocalDate protectionStartDate, LocalDate protectionEndDate);


    List<Vaccine> findByName(String name);

    ResultData<VaccineResponse> save2(SaveVaccineRequest saveVaccineRequest);

    ResultData<List<AnimalResponse4>> getVaccineAndAnimal(String vaccineName, String animalName);

    public ResultData<List<VaccineResponse>> get2(LocalDate protectionStartDate, LocalDate protectionEndDate);

    public ListResult<VaccineResponse> get3(String name);

    public ResultData<VaccineResponse> update2( UpdateVaccineRequest updateVaccineRequest);



    ResultData<List<AnimalResponse4>> getAnimalAllVaccines(String animalName);
}
