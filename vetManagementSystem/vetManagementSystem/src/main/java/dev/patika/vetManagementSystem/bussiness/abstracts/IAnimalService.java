package dev.patika.vetManagementSystem.bussiness.abstracts;

import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Customer;

import java.time.LocalDate;
import java.util.List;

public interface IAnimalService {

    Animal save(Animal animal);

    Animal get(long id);

    Animal update(Animal animal);

    boolean delete (long id);

    List<Animal> findByName(String name);

    Animal getAnimalByName(String name);




}
