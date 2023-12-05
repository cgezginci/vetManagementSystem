package dev.patika.vetManagementSystem.api;

import dev.patika.vetManagementSystem.bussiness.abstracts.IAppointmentService;
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
import dev.patika.vetManagementSystem.dto.request.animal.UpdateAnimalRequest;
import dev.patika.vetManagementSystem.dto.request.availableDate.SaveAvailableDateRequest;
import dev.patika.vetManagementSystem.dto.request.availableDate.UpdateAvailableDate;
import dev.patika.vetManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.vetManagementSystem.dto.response.availableDateResponse.AvailableDateResponse;
import dev.patika.vetManagementSystem.dto.response.availableDateResponse.AvailableDateResponse2;
import dev.patika.vetManagementSystem.entities.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
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

    @Autowired
    private IAppointmentService appointmentService;


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED) //Ekleme
    public ResultData<AvailableDateResponse2> save2(@Valid @RequestBody SaveAvailableDateRequest saveAvailableDateRequest) {
        return this.availableDateService.save2(saveAvailableDateRequest);
    }

    @GetMapping("/{id}") // id ye göre filtreleme
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse2> get2(@PathVariable("id") long id) {
        return this.availableDateService.get2(id);
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

    @PutMapping() //Güncelleme
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse2> update2(@Valid @RequestBody UpdateAvailableDate updateAvailableDate) {
        return this.availableDateService.update2(updateAvailableDate);
    }
}




