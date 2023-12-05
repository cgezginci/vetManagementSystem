package dev.patika.vetManagementSystem.api;

import dev.patika.vetManagementSystem.bussiness.abstracts.IAnimalService;
import dev.patika.vetManagementSystem.bussiness.abstracts.ICustomerService;
import dev.patika.vetManagementSystem.core.config.modelmapper.IModelMapperService;
import dev.patika.vetManagementSystem.core.config.utilies.Msg;
import dev.patika.vetManagementSystem.core.config.utilies.ResultHelper;
import dev.patika.vetManagementSystem.core.exception.NotFoundException;
import dev.patika.vetManagementSystem.core.result.ListResult;
import dev.patika.vetManagementSystem.core.result.Result;
import dev.patika.vetManagementSystem.core.result.ResultData;
import dev.patika.vetManagementSystem.dto.request.animal.SaveAnimalRequest;
import dev.patika.vetManagementSystem.dto.request.animal.UpdateAnimalRequest;
import dev.patika.vetManagementSystem.dto.request.vaccine.SaveVaccineRequest;
import dev.patika.vetManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.vetManagementSystem.dto.response.animal.AnimalResponse2;
import dev.patika.vetManagementSystem.dto.response.vaccine.VaccineResponse;
import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Customer;
import dev.patika.vetManagementSystem.entities.Vaccine;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/animal")
public class AnimalController {
    @Autowired
    private IAnimalService animalService;

    @Autowired
    private IModelMapperService modelMapper;


    @Autowired
    private ICustomerService customerService;


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED) //Ekleme
    public ResultData<AnimalResponse2> save(@Valid @RequestBody SaveAnimalRequest saveAnimalRequest) {
        try {
            Animal response = this.modelMapper.forRequest().map(saveAnimalRequest, Animal.class);
            Customer customer = this.customerService.get(saveAnimalRequest.getCustomer().getId());
            response.setCustomer(customer);
            this.animalService.save(response);
            return ResultHelper.created(this.modelMapper.forResponse().map(response, AnimalResponse2.class));

        }catch (Exception e) {
            return ResultData.error(Msg.VALIDATE_ERROR, "500");
        }

    }

    @GetMapping("/{name}") // isme göre filtreleme
    @ResponseStatus(HttpStatus.OK)
    public ListResult<AnimalResponse2> findByName2(@PathVariable("name") String name){
        return animalService.findByName2(name);
    }



    @DeleteMapping("/{id}") //Silme
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        boolean isDeleted = this.animalService.delete(id);

        if (isDeleted) {
            return ResultHelper.ok();
        } else {
            return ResultHelper.notFoundError(Msg.NOT_FOUND);
        }
    }

    @PutMapping() //Güncelleme
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse2> update(@Valid @RequestBody UpdateAnimalRequest updateAnimalRequest) {
        Long animalId = updateAnimalRequest.getId();
        try {
            Animal updatedAnimal = this.animalService.get(animalId);
            this.modelMapper.forRequest().map(updateAnimalRequest, updatedAnimal);
            Customer customer = this.customerService.get(updateAnimalRequest.getCustomer().getId());
            updatedAnimal.setCustomer(customer);
            this.animalService.update(updatedAnimal);
            AnimalResponse2 animalResponse = this.modelMapper.forResponse().map(updatedAnimal, AnimalResponse2.class);
            return ResultHelper.success(animalResponse);
        }catch (Exception e) {
            return ResultData.error(Msg.NOT_FOUND, "404");
        }

    }


}

