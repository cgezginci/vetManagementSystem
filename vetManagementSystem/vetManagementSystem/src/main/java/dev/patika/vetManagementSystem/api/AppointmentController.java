package dev.patika.vetManagementSystem.api;

import dev.patika.vetManagementSystem.bussiness.abstracts.IAnimalService;
import dev.patika.vetManagementSystem.bussiness.abstracts.IAppointmentService;
import dev.patika.vetManagementSystem.bussiness.abstracts.IDoctorService;
import dev.patika.vetManagementSystem.bussiness.concrets.AppointmentManager;
import dev.patika.vetManagementSystem.core.config.modelmapper.IModelMapperService;
import dev.patika.vetManagementSystem.core.config.utilies.Msg;
import dev.patika.vetManagementSystem.core.config.utilies.ResultHelper;
import dev.patika.vetManagementSystem.core.result.Result;
import dev.patika.vetManagementSystem.core.result.ResultData;
import dev.patika.vetManagementSystem.dto.request.appointment.SaveAppointmentRequest;
import dev.patika.vetManagementSystem.dto.request.availableDate.SaveAvailableDateRequest;
import dev.patika.vetManagementSystem.dto.response.appointment.AppointmentResponse;
import dev.patika.vetManagementSystem.dto.response.availableDateResponse.AvailableDateResponse;
import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Appointment;
import dev.patika.vetManagementSystem.entities.AvailableDate;
import dev.patika.vetManagementSystem.entities.Doctor;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResultData<AppointmentResponse> save(@Valid @RequestBody SaveAppointmentRequest saveAppointmentRequest) {
        try {
            Appointment response = this.modelMapper.forRequest().map(saveAppointmentRequest, Appointment.class);
            Animal animal = this.animalService.get(saveAppointmentRequest.getAnimal().getId());
            Doctor doctor = this.doctorService.get(saveAppointmentRequest.getDoctor().getId());
            response.setDoctor(doctor);
            response.setAnimal(animal);



           //response.getDate = LocalDateTime, doctor.getAvailableDateList.getDate = LocalDate
            boolean isDoctorAvailable = doctor.getAvailableDateList().stream().anyMatch(availableDate -> availableDate.getDate().equals(response.getDate().toLocalDate()));

             boolean isDoctorHasAppointment = doctor.getAppointmentList().stream().anyMatch(appointment -> appointment.getDate().equals(response.getDate()));

            if (!isDoctorAvailable ) {
                return ResultData.error("Doktor bu tarihte çalışmamaktadır", "400");
            }
            if (isDoctorHasAppointment) {
                 return ResultData.error("Doktor bu tarihte başka bir randevusu bulunmaktadır", "400");

            }



            this.appointmentService.save(response);
            return ResultHelper.created(this.modelMapper.forResponse().map(response, AppointmentResponse.class));

        }catch (Exception e) {
            return ResultData.error(Msg.VALIDATE_ERROR, "500");
        }

    }




    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> get (@PathVariable("id") int id) {
        try {
            Appointment appointment = this.appointmentService.get(id);
            AppointmentResponse productResponse = this.modelMapper.forResponse().map(appointment , AppointmentResponse.class);
            return ResultHelper.success(productResponse);
        }catch (Exception e) {
            return ResultData.error(Msg.VALIDATE_ERROR, "500");
        }


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

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public Animal getAppointmentsInDateRange(
            @RequestParam long animalId,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {



            List<Appointment> appointmentList = appointmentManager.getAppointmentsByDateAndAnimal(animalId, startDate, endDate);
            Animal animal = this.animalService.get(animalId);
            animal.setAppointmentList(appointmentList);

        return animal;
    }





}
