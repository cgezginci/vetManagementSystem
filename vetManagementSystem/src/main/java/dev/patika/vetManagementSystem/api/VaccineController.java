package dev.patika.vetManagementSystem.api;

import dev.patika.vetManagementSystem.bussiness.abstracts.IAnimalService;
import dev.patika.vetManagementSystem.bussiness.abstracts.IVaccineService;
import dev.patika.vetManagementSystem.core.config.modelmapper.IModelMapperService;
import dev.patika.vetManagementSystem.core.config.utilies.Msg;
import dev.patika.vetManagementSystem.core.config.utilies.ResultHelper;
import dev.patika.vetManagementSystem.core.exception.NotFoundException;
import dev.patika.vetManagementSystem.core.result.ListResult;
import dev.patika.vetManagementSystem.core.result.Result;
import dev.patika.vetManagementSystem.core.result.ResultData;
import dev.patika.vetManagementSystem.dto.request.vaccine.SaveVaccineRequest;
import dev.patika.vetManagementSystem.dto.request.vaccine.UpdateVaccineRequest;
import dev.patika.vetManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.vetManagementSystem.dto.response.animal.AnimalResponse4;
import dev.patika.vetManagementSystem.dto.response.customer.CustomerResponse;
import dev.patika.vetManagementSystem.dto.response.vaccine.VaccineResponse;
import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Customer;
import dev.patika.vetManagementSystem.entities.Vaccine;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/vaccine")
public class VaccineController {

    @Autowired
    private IVaccineService vaccineService;

    @Autowired
    private IModelMapperService modelMapper;

    @Autowired
    private IAnimalService animalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //Ekleme
    public ResultData<VaccineResponse> save2(@Valid @RequestBody SaveVaccineRequest saveVaccineRequest) {
        return this.vaccineService.save2(saveVaccineRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) //Silme
    public Result delete(@PathVariable("id") long id) {
        boolean isDeleted = this.vaccineService.delete(id);
        if (!isDeleted) {
            return ResultHelper.notFoundError(Msg.NOT_FOUND);
        }
        return ResultHelper.ok();
    }

    @GetMapping("/{vaccineName}/{animalName}") // aşı ve hayvan ismine göre filtreleme
    public ResultData<List<AnimalResponse4>> getVaccineAndAnimal(
            @PathVariable String vaccineName,
            @PathVariable String animalName) {
        return this.vaccineService.getVaccineAndAnimal(vaccineName, animalName);
    }

    @GetMapping("/filter/{animalName}") // hayvan adına göre bütün aşılarını getirme
    public ResultData<List<AnimalResponse4>> getAnimalAndVaccine(
            @PathVariable String animalName) {
        return this.vaccineService.getAnimalAllVaccines(animalName);
    }


    @GetMapping("/date") //Koruyuculuk tarihine göre filtreleme
    public ResultData<List<VaccineResponse>> get2(
            @RequestParam("protectionStartDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate protectionStartDate,
            @RequestParam("protectionEndDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate protectionEndDate){
        return this.vaccineService.get2(protectionStartDate, protectionEndDate);
    }

    @GetMapping("/{name}") // aşı ismine göre filtreleme
    @ResponseStatus(HttpStatus.OK)
    public ListResult<VaccineResponse> get3(@PathVariable("name") String name) {
        return this.vaccineService.get3(name);
    }

    @PutMapping() //Güncelleme
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update2(@Valid @RequestBody UpdateVaccineRequest updateVaccineRequest){
        return this.vaccineService.update2(updateVaccineRequest);
    }

}


