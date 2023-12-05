package dev.patika.vetManagementSystem.api;

import dev.patika.vetManagementSystem.bussiness.abstracts.IDoctorService;
import dev.patika.vetManagementSystem.core.config.modelmapper.IModelMapperService;
import dev.patika.vetManagementSystem.core.config.utilies.Msg;
import dev.patika.vetManagementSystem.core.config.utilies.ResultHelper;
import dev.patika.vetManagementSystem.core.exception.NotFoundException;
import dev.patika.vetManagementSystem.core.result.ListResult;
import dev.patika.vetManagementSystem.core.result.Result;
import dev.patika.vetManagementSystem.core.result.ResultData;
import dev.patika.vetManagementSystem.dto.request.customer.SaveCustomerRequest;
import dev.patika.vetManagementSystem.dto.request.doctor.SaveDoctorRequest;
import dev.patika.vetManagementSystem.dto.response.customer.CustomerResponse;
import dev.patika.vetManagementSystem.dto.response.doctor.DoctorResponse;
import dev.patika.vetManagementSystem.entities.Customer;
import dev.patika.vetManagementSystem.entities.Doctor;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/doctor")
public class DoctorController {

    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private IModelMapperService modelMapper;


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED) //Ekleme
    public ResultData<DoctorResponse> save(@Valid @RequestBody SaveDoctorRequest saveDoctorRequest) {
        try {
            Doctor response = this.modelMapper.forRequest().map(saveDoctorRequest, Doctor.class);
            this.doctorService.save(response);
            return ResultHelper.created(this.modelMapper.forResponse().map(response, DoctorResponse.class));

        }catch (Exception e) {
            return ResultData.error(Msg.VALIDATE_ERROR, "500");
        }

    }

    @GetMapping("/{name}") // isme göre filtreleme
    @ResponseStatus(HttpStatus.OK)
    public ListResult<DoctorResponse> findByName(@PathVariable("name") String name) {
        try {
            List<Doctor> doctors = this.doctorService.findByName(name);

            List<DoctorResponse> doctorResponses = doctors.stream()
                    .map(doctor -> this.modelMapper.forResponse().map(doctor, DoctorResponse.class))
                    .collect(Collectors.toList());

            return ResultHelper.successList(doctorResponses);
        } catch (NotFoundException ex) {
            return ResultHelper.notFoundErrorList(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}") //Silme
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        boolean isDeleted = this.doctorService.delete(id);

        if (isDeleted) {
            return ResultHelper.ok();
        } else {
            return ResultHelper.notFoundError(Msg.NOT_FOUND);
        }
    }




}
