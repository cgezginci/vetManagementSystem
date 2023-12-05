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
import dev.patika.vetManagementSystem.dto.response.animal.AnimalResponse;
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

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@Valid @RequestBody SaveVaccineRequest saveVaccineRequest) {
        try {
            Vaccine newVaccine = this.modelMapper.forRequest().map(saveVaccineRequest, Vaccine.class);

            Animal animal = this.animalService.get(saveVaccineRequest.getAnimal().getId());
            newVaccine.setAnimal(animal);

            List<Vaccine> existingVaccines = animal.getVaccineList();

            for (Vaccine existingVaccine : existingVaccines) {
                if (existingVaccine.getName().equals(newVaccine.getName()) && existingVaccine.getProtectionEndDate() != null && LocalDate.now().isBefore(existingVaccine.getProtectionEndDate())) {
                    return ResultData.error("Aynı isimdeki aşı için koruyuculuk bitiş tarihi devam ediyor.", "500");
                }
            }

            this.vaccineService.save(newVaccine);

            return ResultHelper.created(this.modelMapper.forResponse().map(newVaccine, VaccineResponse.class));
        } catch (Exception e) {
            return ResultData.error(Msg.VALIDATE_ERROR, "500");
        }
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") long id) {
        boolean isDeleted = this.vaccineService.delete(id);
        if (!isDeleted) {
            return ResultHelper.notFoundError(Msg.NOT_FOUND);
        }

        return ResultHelper.ok();
    }

    @GetMapping("/{name}/{animalName}") //Aşı ismine ve hayvan ismine göre filtreleme
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAnimals(@PathVariable("name") String name, @PathVariable("animalName") String animalName) {
        try {
            Vaccine vaccine = this.vaccineService.getVaccineByName(name);

            List<Animal> matchingAnimals = Collections.singletonList(vaccine.getAnimal()).stream()
                    .filter(animal -> animal.getName().equalsIgnoreCase(animalName))
                    .collect(Collectors.toList());

            if (matchingAnimals.size() == 0) {
                return ResultData.error("Hayvan bulunamadı.", "404");
            }

            List<AnimalResponse> animalResponses = matchingAnimals.stream()
                    .map(animal -> {
                        AnimalResponse response = modelMapper.forResponse().map(animal, AnimalResponse.class);
                        response.setVaccineList(Collections.singletonList(modelMapper.forResponse().map(vaccine, Vaccine.class)));
                        response.setName(animalName);
                        return response;
                    })
                    .collect(Collectors.toList());

            if (vaccine == null) {
                return ResultData.error("Aşı bulunamadı.", "404");
            }

            return ResultHelper.success(animalResponses);
        } catch (Exception e) {
            return ResultData.error(Msg.VALIDATE_ERROR, "500");
        }
    }

    @GetMapping("/date") //Koruyuculuk tarihine göre filtreleme
    public ResultData<List<VaccineResponse>> getVaccinesByDate(
            @RequestParam("protectionStartDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate protectionStartDate,
            @RequestParam("protectionEndDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate protectionEndDate)  {

        if (protectionStartDate.isAfter(protectionEndDate)) {
            return ResultData.error("Başlangıç tarihi, bitiş tarihinden sonra olamaz.", "404");
        }
        try {
            List<Vaccine> vaccinesWithUpcomingProtection = vaccineService.getVaccinesByDate(protectionStartDate , protectionEndDate);

            if (vaccinesWithUpcomingProtection.isEmpty()) {
                return ResultData.error("Belirtilen tarih aralığında aşı bulunmamaktadır.", "404");
            }

            List<VaccineResponse> vaccineResponses = vaccinesWithUpcomingProtection.stream()
                    .map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                    .collect(Collectors.toList());

            return ResultHelper.success(vaccineResponses);
        } catch (NotFoundException ex) {
            return ResultData.error(Msg.NOT_FOUND, "404");
        }
    }

    @GetMapping("/{name}") // isme göre filtreleme
    @ResponseStatus(HttpStatus.OK)
    public ListResult<VaccineResponse> findByName(@PathVariable("name") String name) {
        try {
            List<Vaccine> vaccines = this.vaccineService.findByName(name);

            List<VaccineResponse> vaccineResponses = vaccines.stream()
                    .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                    .collect(Collectors.toList());

            return ResultHelper.successList(vaccineResponses);
        } catch (NotFoundException ex) {
            return ResultHelper.notFoundErrorList(ex.getMessage());
        }
    }

    @PutMapping("/{vaccineId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update(@PathVariable Long vaccineId, @Valid @RequestBody SaveVaccineRequest saveVaccineRequest) {
        try {
            // Aşıyı bul
            Vaccine existingVaccine = this.vaccineService.get(vaccineId);

            // Yeni bilgileri güncelle
            this.modelMapper.forRequest().map(saveVaccineRequest, existingVaccine);

            // Eğer hayvan değiştiyse güncelle
            Animal animal = this.animalService.get(saveVaccineRequest.getAnimal().getId());
            existingVaccine.setAnimal(animal);

            // Kontrol et: Aynı isimdeki aşı için koruyuculuk bitiş tarihi devam ediyor mu?
            List<Vaccine> existingVaccines = animal.getVaccineList();
            for (Vaccine vaccine : existingVaccines) {
                if (vaccine.getId() == existingVaccine.getId()
                        && vaccine.getName().equals(existingVaccine.getName())
                        && vaccine.getProtectionEndDate() != null
                        && LocalDate.now().isBefore(vaccine.getProtectionEndDate())) {
                    return ResultData.error("Aynı isimdeki aşı için koruyuculuk bitiş tarihi devam ediyor.", "500");
                }
            }

            // Güncelleme işlemini kaydet
            this.vaccineService.save(existingVaccine);

            return ResultHelper.success(this.modelMapper.forResponse().map(existingVaccine, VaccineResponse.class));
        } catch (NotFoundException e) {
            return ResultData.error("Aşı bulunamadı.", "404");
        } catch (Exception e) {
            return ResultData.error(Msg.VALIDATE_ERROR, "500");
        }
    }








}
