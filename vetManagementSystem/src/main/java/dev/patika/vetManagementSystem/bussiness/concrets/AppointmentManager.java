package dev.patika.vetManagementSystem.bussiness.concrets;

import dev.patika.vetManagementSystem.bussiness.abstracts.IAnimalService;
import dev.patika.vetManagementSystem.bussiness.abstracts.IAppointmentService;
import dev.patika.vetManagementSystem.bussiness.abstracts.IDoctorService;
import dev.patika.vetManagementSystem.core.config.modelmapper.IModelMapperService;
import dev.patika.vetManagementSystem.core.config.utilies.Msg;
import dev.patika.vetManagementSystem.core.config.utilies.ResultHelper;
import dev.patika.vetManagementSystem.core.exception.DataNotFoundException;
import dev.patika.vetManagementSystem.core.exception.NotFoundException;
import dev.patika.vetManagementSystem.core.result.ListResult;
import dev.patika.vetManagementSystem.core.result.ResultData;
import dev.patika.vetManagementSystem.dao.AppointmentRepo;
import dev.patika.vetManagementSystem.dto.request.appointment.SaveAppointmentRequest;
import dev.patika.vetManagementSystem.dto.request.appointment.UpdateAppointmentRequest;
import dev.patika.vetManagementSystem.dto.response.appointment.AppointmentResponse;
import dev.patika.vetManagementSystem.dto.response.appointment.AppointmentResponse2;
import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Appointment;
import dev.patika.vetManagementSystem.entities.AvailableDate;
import dev.patika.vetManagementSystem.entities.Doctor;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentManager implements IAppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private IModelMapperService modelMapper;

    @Autowired
    private IAnimalService animalService;


    @Override
    public ResultData<AppointmentResponse2> save2(SaveAppointmentRequest saveAppointmentRequest) {
        try {
            Appointment response = this.modelMapper.forRequest().map(saveAppointmentRequest, Appointment.class);
            Animal animal = this.animalService.get(saveAppointmentRequest.getAnimal().getId());
            Doctor doctor = this.doctorService.get(saveAppointmentRequest.getDoctor().getId());
            response.setDoctor(doctor);
            response.setAnimal(animal);


            boolean isDoctorAvailable = doctor.getAvailableDateList().stream().anyMatch(availableDate -> availableDate.getDate().equals(response.getDate().toLocalDate()));

            boolean isDoctorHasAppointment = doctor.getAppointmentList().stream().anyMatch(appointment -> appointment.getDate().equals(response.getDate()));

            if (!isDoctorAvailable ) {
                return ResultData.error("Doktor bu tarihte çalışmamaktadır", "400");
            }
            if (isDoctorHasAppointment) {
                return ResultData.error("Doktor bu saatte başka bir randevusu bulunmaktadır", "400");

            }

            doctor.getAppointmentList().add(response);
            this.appointmentRepo.save(response);
            return ResultHelper.created(this.modelMapper.forResponse().map(response, AppointmentResponse2.class));
        }catch (Exception e) {
            return ResultData.error(Msg.VALIDATE_ERROR, "500");
        }
    }



    @Override
    public Appointment save(Appointment appointment) {
        Appointment savedAppointment = this.appointmentRepo.save(appointment);

        Doctor doctor = this.doctorService.get(appointment.getDoctor().getId());

        doctor.getAppointmentList().add(savedAppointment);

        this.doctorService.update(doctor);

        return savedAppointment;
    }


    @Override
    public ResultData<AppointmentResponse2> get2 (long id) {
        try {
            Appointment appointment = this.appointmentRepo.findById(id).orElseThrow();
            AppointmentResponse2 productResponse = this.modelMapper.forResponse().map(appointment, AppointmentResponse2.class);
            return ResultHelper.success(productResponse);
        } catch (Exception e) {
            return ResultData.error(Msg.VALIDATE_ERROR, "500");
        }
    }

    @Override
    public ResultData<AppointmentResponse2> update2(UpdateAppointmentRequest updateAppointmentRequest) {
        Long appointmentId = updateAppointmentRequest.getId();
        try {
            Appointment response = this.appointmentRepo.findById(appointmentId).orElseThrow();
            this.modelMapper.forRequest().map(updateAppointmentRequest, response);
            Animal animal = this.animalService.get(updateAppointmentRequest.getAnimal().getId());
            Doctor doctor = this.doctorService.get(updateAppointmentRequest.getDoctor().getId());
            response.setDoctor(doctor);
            response.setAnimal(animal);

            LocalDateTime newAppointmentDateTime = response.getDate();

            boolean isDoctorAvailable = doctor.getAvailableDateList().stream().anyMatch(availableDate -> availableDate.getDate().equals(response.getDate().toLocalDate()));
            if (!isDoctorAvailable ) {
                return ResultData.error("Doktor bu tarihte çalışmamaktadır", "400");
            }
            boolean isDoctorTimeAvailable = doctor.getAppointmentList()
                    .stream()
                    .anyMatch(appointment ->
                            appointment.getId() != response.getId() && // Güncellendiği randevuyu kontrol etmiyoruz
                                    appointment.getDate().getHour() == newAppointmentDateTime.getHour() &&
                                    appointment.getDate().getMinute() == newAppointmentDateTime.getMinute()
                    );

            if (isDoctorTimeAvailable) {
                return ResultData.error("Doktor bu saatte zaten bir randevusu var.", "400");
            }
            this.appointmentRepo.save(response);

            AppointmentResponse2 appointmentResponse = this.modelMapper.forResponse().map(response, AppointmentResponse2.class);
            return ResultHelper.success(appointmentResponse);
    }catch (Exception e) {
            return ResultData.error(Msg.VALIDATE_ERROR, "500");
        }
    }



    @Override
    public Appointment get(long id) {
        return this.appointmentRepo.findById(id).orElseThrow(() ->  new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public Appointment update(Appointment appointment) {
        return this.appointmentRepo.save(appointment);


    }

    @Override
    public boolean delete(long id) {
        try {
            Appointment appointment = this.get(id);
            this.appointmentRepo.delete(appointment);
            return true;
        } catch (NotFoundException ex) {
            return false;
        }

    }

    @Override
    public List<Appointment> getAppointmentsByDateAndAnimal(long animalId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<Appointment> appointments = appointmentRepo.findByDateBetweenAndAnimal_IdOrderByDate(startDateTime, endDateTime, animalId);



        return appointments;
    }

    @Override
    public List<AppointmentResponse2> getAppointmentsByAnimalNameAndDateRange(String animalName, LocalDate startDate, LocalDate endDate) {
        List<Animal> animals = animalService.getAnimalByNames(animalName);

        return animals.stream()
                .flatMap(animal -> {
                    Long animalId = animal.getId();
                    return appointmentRepo.findByDateBetweenAndAnimal_IdOrderByDate(
                                    startDate.atStartOfDay(),
                                    endDate.atTime(23, 59, 59),
                                    animalId
                            ).stream()
                            .map(appointment -> {
                                appointment.setAnimal(animal);  // Burada Appointment nesnesine animal bilgisini set ediyoruz
                                return AppointmentResponse2.fromAppointmentAndDoctorAndAnimal(appointment);
                            });
                })
                .toList();
    }


    @Override
    public List<AppointmentResponse2> getAppointmentsByDoctorNameAndDateRange(String doctorName, LocalDate startDate, LocalDate endDate) {
        List<Doctor> doctors = doctorService.getDoctorByNames(doctorName);



        // Tarih aralığındaki randevuları hayvanlara ekleyerek döndür
        return doctors.stream()
                .flatMap(doctor -> {
                    Long doctorId = doctor.getId();
                    return appointmentRepo.findByDateBetweenAndDoctor_IdOrderByDate(
                                    startDate.atStartOfDay(),
                                    endDate.atTime(23, 59, 59),
                                    doctorId
                            ).stream()
                            .map(appointment -> {
                                appointment.setDoctor(doctor);
                                return AppointmentResponse2.fromAppointmentAndDoctorAndAnimal(appointment);
                            });
                })
                .toList();
    }




}
