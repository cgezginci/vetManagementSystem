package dev.patika.vetManagementSystem.bussiness.concrets;

import dev.patika.vetManagementSystem.bussiness.abstracts.IAnimalService;
import dev.patika.vetManagementSystem.bussiness.abstracts.ICustomerService;
import dev.patika.vetManagementSystem.core.config.modelmapper.IModelMapperService;
import dev.patika.vetManagementSystem.core.config.utilies.Msg;
import dev.patika.vetManagementSystem.core.config.utilies.ResultHelper;
import dev.patika.vetManagementSystem.core.exception.NotFoundException;
import dev.patika.vetManagementSystem.core.result.ListResult;
import dev.patika.vetManagementSystem.core.result.ResultData;
import dev.patika.vetManagementSystem.dao.AnimalRepo;
import dev.patika.vetManagementSystem.dao.CustomerRepo;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CustomerManager implements ICustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private IAnimalService animalService;
    @Autowired
    private IModelMapperService modelMapper;

    @Override
    public Customer save(Customer customer) {

        return customerRepo.save(customer);
    }

    @Override
    public ResultData<CustomerResponse2> save2(SaveCustomerRequest saveCustomerRequest) {
        try {
            Customer response = this.modelMapper.forRequest().map(saveCustomerRequest, Customer.class);
            this.customerRepo.save(response);
            return ResultHelper.created(this.modelMapper.forResponse().map(response, CustomerResponse2.class));

        }catch (Exception e) {
            return ResultData.error(Msg.VALIDATE_ERROR, "500");
        }
    }

    @Override
    public ListResult<CustomerResponse3> get2(String name) {
        try {
            List<Customer> customerList = this.customerRepo.findByNameIgnoreCase(name);
            List<CustomerResponse3> customerResponseList = customerList.stream().map(customer -> this.modelMapper.forResponse().map(customer, CustomerResponse3.class)).collect(Collectors.toList());
            if (customerResponseList.isEmpty()) {
                throw new NotFoundException(Msg.NOT_FOUND);
            }
            return ResultHelper.successList(customerResponseList);
        }catch (Exception e) {
            return ResultHelper.notFoundErrorList(e.getMessage());
        }
    }

    @Override
    public ResultData<CustomerResponse2> update2(UpdateCustomerRequest updateCustomerRequest) {
        Long customerId = updateCustomerRequest.getId();
        try {
            Customer updatedCustomer = this.customerRepo.findById(customerId).orElseThrow();
            this.modelMapper.forRequest().map(updateCustomerRequest, updatedCustomer);

            this.customerRepo.save(updatedCustomer);
            CustomerResponse2 customerResponse2 = this.modelMapper.forResponse().map(updatedCustomer, CustomerResponse2.class);
            return ResultHelper.success(customerResponse2);
        }catch (Exception e) {
            return ResultData.error(Msg.NOT_FOUND, "500");
        }
    }

    @Override
    public ResultData<List<AnimalResponse2>> get3(String name, String animalName) {
        try {

            Customer customer = this.customerRepo.findByNameIgnoreCase(name)
                    .stream()
                    .findAny()
                    .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));


            List<Animal> matchingAnimals = customer.getAnimalList().stream()
                    .filter(animal -> animal.getName().equalsIgnoreCase(animalName))
                    .collect(Collectors.toList());

            if (matchingAnimals.isEmpty()) {
                throw new NotFoundException(Msg.NOT_FOUND);
            }


            List<AnimalResponse2> animalResponses = matchingAnimals.stream()
                    .map(animal -> AnimalResponse2.AnimalAndCustomer(animal, customer))
                    .peek(response -> response.setName(animalName))
                    .collect(Collectors.toList());

            return ResultHelper.success(animalResponses);
        } catch (NotFoundException ex) {
            return ResultData.error(Msg.NOT_FOUND, "404");
        }
    }


    @Override
    public Customer get(long id) {
        return customerRepo.findById(id)
                .orElseThrow(() ->  new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public Customer update(Customer customer) {
        this.get(customer.getId());
        return customerRepo.save(customer);
    }

    @Override
    public boolean delete(long id) {
        try {
            Customer customer = this.get(id);
            this.customerRepo.delete(customer);
            return true;
        } catch (NotFoundException ex) {
            return false;
        }
    }

    @Override
    public List<Customer> findByName(String name) {
        List<Customer> customers = customerRepo.findByNameIgnoreCase(name);
        if (customers.isEmpty()) {
            throw new NotFoundException(Msg.NOT_FOUND);
        }

        return customers;
    }

    @Override
    public Customer getCustomerByName(String name) {
        return customerRepo.findByNameIgnoreCase(name)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public List<Vaccine> getVaccinesByAnimalId(long animalId) {
        Animal animal = animalService.get(animalId);
        return animal.getVaccineList();
    }




}
