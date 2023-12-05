package dev.patika.vetManagementSystem.api;

import dev.patika.vetManagementSystem.bussiness.abstracts.IAnimalService;
import dev.patika.vetManagementSystem.bussiness.abstracts.IAppointmentService;
import dev.patika.vetManagementSystem.bussiness.abstracts.IDoctorService;
import dev.patika.vetManagementSystem.bussiness.concrets.AppointmentManager;
import dev.patika.vetManagementSystem.core.config.modelmapper.IModelMapperService;
import dev.patika.vetManagementSystem.core.config.utilies.Msg;
import dev.patika.vetManagementSystem.core.config.utilies.ResultHelper;
import dev.patika.vetManagementSystem.core.result.ListResult;
import dev.patika.vetManagementSystem.core.result.Result;
import dev.patika.vetManagementSystem.core.result.ResultData;
import dev.patika.vetManagementSystem.dto.request.appointment.SaveAppointmentRequest;
import dev.patika.vetManagementSystem.dto.request.appointment.UpdateAppointmentRequest;
import dev.patika.vetManagementSystem.dto.response.appointment.AppointmentResponse;
import dev.patika.vetManagementSystem.dto.response.appointment.AppointmentResponse2;
import dev.patika.vetManagementSystem.entities.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/appointment")
public class AppointmentController {

    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private IModelMapperService modelMapper;

    @Autowired
    private IAnimalService animalService;

    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private AppointmentManager appointmentManager;




    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED) //Ekleme
    public ResultData<AppointmentResponse2> save2(@Valid @RequestBody SaveAppointmentRequest saveAppointmentRequest) {
        return appointmentManager.save2(saveAppointmentRequest);
    }




    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) //Id'ye göre getirme
    public ResultData<AppointmentResponse2> get(@PathVariable("id") long id) {
        return appointmentService.get2(id);
    }


    @DeleteMapping("/{id}") //Silme
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        boolean isDeleted = this.appointmentService.delete(id);

        if (isDeleted) {
            return ResultHelper.ok();
        } else {
            return ResultHelper.notFoundError(Msg.NOT_FOUND);
        }
    }



    @GetMapping("/filter") //hayvan adı ve tarih aralığına göre getirme
    public ListResult<AppointmentResponse2> getAppointmentsInDateRange(
            @RequestParam("animalName") String animalName,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        List<AppointmentResponse2> appointments = appointmentManager.getAppointmentsByAnimalNameAndDateRange(animalName, startDate, endDate);
        if (appointments.isEmpty()) {
            return ResultHelper.notFoundErrorList(Msg.NOT_FOUND);
        }
        return ResultHelper.successList(appointments);
    }


    @GetMapping("/filtered") //doktor adı ve tarih aralığına göre getirme
    public ListResult<AppointmentResponse2> getAppointmentsInDateRangeAndDoctorName(
            @RequestParam("doctorName") String doctorName,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        List<AppointmentResponse2> appointments = appointmentManager.getAppointmentsByDoctorNameAndDateRange(doctorName, startDate, endDate);
        if (appointments.isEmpty()) {
            return ResultHelper.notFoundErrorList(Msg.NOT_FOUND);
        }
        return ResultHelper.successList(appointments);
    }




    @PutMapping() //Güncelleme
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse2> update2(@Valid @RequestBody UpdateAppointmentRequest updateAppointmentRequest){
        return appointmentService.update2(updateAppointmentRequest);
    }



}



