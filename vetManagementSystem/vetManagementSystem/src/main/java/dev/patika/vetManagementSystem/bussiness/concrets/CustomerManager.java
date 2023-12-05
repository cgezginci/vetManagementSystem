package dev.patika.vetManagementSystem.bussiness.concrets;

import dev.patika.vetManagementSystem.bussiness.abstracts.IAnimalService;
import dev.patika.vetManagementSystem.bussiness.abstracts.ICustomerService;
import dev.patika.vetManagementSystem.core.config.utilies.Msg;
import dev.patika.vetManagementSystem.core.exception.NotFoundException;
import dev.patika.vetManagementSystem.dao.AnimalRepo;
import dev.patika.vetManagementSystem.dao.CustomerRepo;
import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Customer;

import dev.patika.vetManagementSystem.entities.Vaccine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerManager implements ICustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private IAnimalService animalService;

    @Override
    public Customer save(Customer customer) {

        return customerRepo.save(customer);
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
