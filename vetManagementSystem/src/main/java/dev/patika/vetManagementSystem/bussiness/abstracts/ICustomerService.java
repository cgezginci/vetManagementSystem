package dev.patika.vetManagementSystem.bussiness.abstracts;

import dev.patika.vetManagementSystem.core.result.ListResult;
import dev.patika.vetManagementSystem.core.result.ResultData;
import dev.patika.vetManagementSystem.dto.request.customer.SaveCustomerRequest;
import dev.patika.vetManagementSystem.dto.request.customer.UpdateCustomerRequest;
import dev.patika.vetManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.vetManagementSystem.dto.response.animal.AnimalResponse2;
import dev.patika.vetManagementSystem.dto.response.customer.CustomerResponse;
import dev.patika.vetManagementSystem.dto.response.customer.CustomerResponse2;
import dev.patika.vetManagementSystem.dto.response.customer.CustomerResponse3;
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

    ResultData<CustomerResponse2> save2(SaveCustomerRequest saveCustomerRequest);

  ListResult<CustomerResponse3> get2(String name);

     ResultData<CustomerResponse2> update2(UpdateCustomerRequest updateCustomerRequest);

    ResultData<List<AnimalResponse2>> get3(String name , String animalName);


}
