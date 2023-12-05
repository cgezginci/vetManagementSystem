package dev.patika.vetManagementSystem.api;


import dev.patika.vetManagementSystem.bussiness.abstracts.ICustomerService;
import dev.patika.vetManagementSystem.core.config.modelmapper.IModelMapperService;
import dev.patika.vetManagementSystem.core.config.utilies.Msg;
import dev.patika.vetManagementSystem.core.config.utilies.ResultHelper;
import dev.patika.vetManagementSystem.core.exception.NotFoundException;
import dev.patika.vetManagementSystem.core.result.ListResult;
import dev.patika.vetManagementSystem.core.result.Result;
import dev.patika.vetManagementSystem.core.result.ResultData;
import dev.patika.vetManagementSystem.dto.request.animal.UpdateAnimalRequest;
import dev.patika.vetManagementSystem.dto.request.customer.SaveCustomerRequest;
import dev.patika.vetManagementSystem.dto.request.customer.UpdateCustomerRequest;
import dev.patika.vetManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.vetManagementSystem.dto.response.customer.CustomerResponse;
import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Customer;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IModelMapperService modelMapper;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED) //Ekleme
    public ResultData<CustomerResponse> save(@Valid @RequestBody SaveCustomerRequest saveCustomerRequest) {
        try {
            Customer response = this.modelMapper.forRequest().map(saveCustomerRequest, Customer.class);
            this.customerService.save(response);
            return ResultHelper.created(this.modelMapper.forResponse().map(response, CustomerResponse.class));

        }catch (Exception e) {
            return ResultData.error(Msg.VALIDATE_ERROR, "500");
        }

    }


    @GetMapping("/{name}") // isme göre filtreleme
    @ResponseStatus(HttpStatus.OK)
    public ListResult<CustomerResponse> findByName(@PathVariable("name") String name) {
        try {
            List<Customer> customers = this.customerService.findByName(name);

            List<CustomerResponse> customerResponses = customers.stream()
                    .map(customer -> this.modelMapper.forResponse().map(customer, CustomerResponse.class))
                    .collect(Collectors.toList());

            return ResultHelper.successList(customerResponses);
        } catch (NotFoundException ex) {
            return ResultHelper.notFoundErrorList(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}") //Silme
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        boolean isDeleted = this.customerService.delete(id);

        if (isDeleted) {
            return ResultHelper.ok();
        } else {
            return ResultHelper.notFoundError(Msg.NOT_FOUND);
        }
    }

    @PutMapping() //Güncelleme
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> update(@Valid @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        Long customerId = updateCustomerRequest.getId();
        try {
            Customer updatedCustomer = this.customerService.get(customerId);
            this.modelMapper.forRequest().map(updateCustomerRequest, updatedCustomer);

            this.customerService.update(updatedCustomer);
            CustomerResponse customerResponse = this.modelMapper.forResponse().map(updatedCustomer, CustomerResponse.class);
            return ResultHelper.success(customerResponse);
        }catch (Exception e) {
            return ResultData.error(Msg.NOT_FOUND, "500");
        }
    }

    @GetMapping("/{name}/{animalName}") //Müşteri ismine göre hayvan ismi getirme eğer aynı isimde başka hayvan varsa onuda getirir
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAnimals(@PathVariable("name") String name, @PathVariable("animalName") String animalName) {
        try {

            Customer customer = this.customerService.getCustomerByName(name);


            List<Animal> matchingAnimals = customer.getAnimalList().stream()
                    .filter(animal -> animal.getName().equalsIgnoreCase(animalName))
                    .collect(Collectors.toList());


            List<AnimalResponse> animalResponses = matchingAnimals.stream()
                    .map(animal -> AnimalResponse.saveAnimalAndCustomer(animal, customer))
                    .peek(response -> response.setName(animalName))
                    .collect(Collectors.toList());

            return ResultHelper.success(animalResponses);
        } catch (NotFoundException ex) {
            return ResultData.error(Msg.NOT_FOUND, "404");
        }
    }







}
