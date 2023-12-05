package dev.patika.vetManagementSystem.bussiness.abstracts;

import dev.patika.vetManagementSystem.core.result.ListResult;
import dev.patika.vetManagementSystem.core.result.ResultData;
import dev.patika.vetManagementSystem.dto.request.animal.SaveAnimalRequest;
import dev.patika.vetManagementSystem.dto.request.appointment.SaveAppointmentRequest;
import dev.patika.vetManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.vetManagementSystem.dto.response.animal.AnimalResponse2;
import dev.patika.vetManagementSystem.dto.response.appointment.AppointmentResponse;
import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public interface IAnimalService {




    Animal save(Animal animal);

    Animal get(long id);

    Animal update(Animal animal);

    boolean delete (long id);

    List<Animal> findByName(String name);

    Animal getAnimalByName(String name);

    List<Animal> getAnimalByNames(String name);


    Animal findByNames(String name);

    //ResultData<AnimalResponse> save2(SaveAnimalRequest saveAnimalRequest);

    ListResult<AnimalResponse2> findByName2(String name);



}
