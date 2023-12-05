package dev.patika.vetManagementSystem.api;

import dev.patika.vetManagementSystem.bussiness.abstracts.IAvailableDateService;
import dev.patika.vetManagementSystem.bussiness.abstracts.IDoctorService;
import dev.patika.vetManagementSystem.core.config.modelmapper.IModelMapperService;
import dev.patika.vetManagementSystem.core.config.utilies.Msg;
import dev.patika.vetManagementSystem.core.config.utilies.ResultHelper;
import dev.patika.vetManagementSystem.core.exception.NotFoundException;
import dev.patika.vetManagementSystem.core.result.ListResult;
import dev.patika.vetManagementSystem.core.result.Result;
import dev.patika.vetManagementSystem.core.result.ResultData;
import dev.patika.vetManagementSystem.dto.request.animal.SaveAnimalRequest;
import dev.patika.vetManagementSystem.dto.request.availableDate.SaveAvailableDateRequest;
import dev.patika.vetManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.vetManagementSystem.dto.response.availableDateResponse.AvailableDateResponse;
import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.AvailableDate;
import dev.patika.vetManagementSystem.entities.Customer;
import dev.patika.vetManagementSystem.entities.Doctor;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/available-date")
public class AvailableDateController {

    @Autowired
    private IAvailableDateService availableDateService;

    @Autowired
    private IModelMapperService modelMapper;

    @Autowired
    private IDoctorService doctorService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED) //Ekleme
    public ResultData<AvailableDateResponse> save(@Valid @RequestBody SaveAvailableDateRequest saveAvailableDateRequest) {
        try {
            AvailableDate response = this.modelMapper.forRequest().map(saveAvailableDateRequest, AvailableDate.class);
            Doctor doctor = this.doctorService.get(saveAvailableDateRequest.getDoctor().getId());
            response.setDoctor(doctor);
            this.availableDateService.save(response);
            return ResultHelper.created(this.modelMapper.forResponse().map(response, AvailableDateResponse.class));

        }catch (Exception e) {
            return ResultData.error(Msg.VALIDATE_ERROR, "500");
        }

    }

    @GetMapping("/{id}") // id ye göre filtreleme
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> get(@PathVariable("id") long id) {
    try {
        AvailableDate availableDate = this.availableDateService.get(id);
        AvailableDateResponse availableDateResponse = this.modelMapper.forResponse().map(availableDate, AvailableDateResponse.class);
        return ResultHelper.success(availableDateResponse);

    }catch (Exception e) {
        return ResultData.error(Msg.NOT_FOUND, "500");

    }


    }

    @DeleteMapping("/{id}") //Silme
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        boolean isDeleted = this.availableDateService.delete(id);

        if (isDeleted) {
            return ResultHelper.ok();
        } else {
            return ResultHelper.notFoundError(Msg.NOT_FOUND);
        }
    }


}
