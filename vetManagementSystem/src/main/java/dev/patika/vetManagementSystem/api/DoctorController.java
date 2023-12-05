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
import dev.patika.vetManagementSystem.dto.request.customer.UpdateCustomerRequest;
import dev.patika.vetManagementSystem.dto.request.doctor.SaveDoctorRequest;
import dev.patika.vetManagementSystem.dto.request.doctor.UpdateDoctorRequest;
import dev.patika.vetManagementSystem.dto.response.customer.CustomerResponse;
import dev.patika.vetManagementSystem.dto.response.doctor.DoctorResponse;
import dev.patika.vetManagementSystem.dto.response.doctor.DoctorResponse2;
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
    public ResultData<DoctorResponse2> save2(@Valid @RequestBody SaveDoctorRequest saveDoctorRequest){
        return this.doctorService.save2(saveDoctorRequest);

    }

    @GetMapping("/{name}") // isme göre filtreleme eğer müsait günü ve randevusu varsa gösterir
    @ResponseStatus(HttpStatus.OK)
    public ListResult<DoctorResponse> findByName2(@PathVariable("name") String name) {
        return this.doctorService.findByName2(name);
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

    @PutMapping() //Güncelleme
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse2> update2(@Valid @RequestBody UpdateDoctorRequest updateDoctorRequest) {
        return this.doctorService.update2(updateDoctorRequest);
    }


}

