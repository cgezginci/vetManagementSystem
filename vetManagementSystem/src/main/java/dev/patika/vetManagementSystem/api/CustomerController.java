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
import dev.patika.vetManagementSystem.dto.response.animal.AnimalResponse2;
import dev.patika.vetManagementSystem.dto.response.customer.CustomerResponse;
import dev.patika.vetManagementSystem.dto.response.customer.CustomerResponse2;
import dev.patika.vetManagementSystem.dto.response.customer.CustomerResponse3;
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


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //Ekleme
    public ResultData<CustomerResponse2> save(@Valid @RequestBody SaveCustomerRequest saveCustomerRequest){
        return this.customerService.save2(saveCustomerRequest);
    }


    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK) //İsme göre filtreleme eğer hayvan eklenmişse sahip olduğu hayvanların bilgilerini getirir
    public ListResult<CustomerResponse3> get2(@PathVariable("name") String name){
        return this.customerService.get2(name);
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


    @PutMapping //Güncelleme
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse2> update(@Valid @RequestBody UpdateCustomerRequest updateCustomerRequest){
        return this.customerService.update2(updateCustomerRequest);
    }


    @GetMapping("/{name}/{animalName}") //Müşteri ismine göre hayvan ismi getirme eğer aynı isimde başka hayvan varsa onuda getirir
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse2>> get3(@PathVariable("name") String name, @PathVariable("animalName") String animalName) {
        return this.customerService.get3(name, animalName);

    }
}
