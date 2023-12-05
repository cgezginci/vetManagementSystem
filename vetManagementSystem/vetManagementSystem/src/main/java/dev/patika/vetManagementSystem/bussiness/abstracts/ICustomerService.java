package dev.patika.vetManagementSystem.bussiness.abstracts;

import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Customer;
import dev.patika.vetManagementSystem.entities.Vaccine;

import java.util.List;

public interface ICustomerService {

    Customer save(Customer customer);
    Customer get(long id);
    Customer update(Customer customer);
    boolean delete(long id);

    List<Customer> findByName(String name);

    Customer getCustomerByName(String name);

    List<Vaccine> getVaccinesByAnimalId(long animalId);


}
