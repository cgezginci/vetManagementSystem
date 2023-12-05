package dev.patika.vetManagementSystem.bussiness.concrets;

import dev.patika.vetManagementSystem.bussiness.abstracts.IAppointmentService;
import dev.patika.vetManagementSystem.bussiness.abstracts.IAvailableDateService;
import dev.patika.vetManagementSystem.bussiness.abstracts.IDoctorService;
import dev.patika.vetManagementSystem.core.config.modelmapper.IModelMapperService;
import dev.patika.vetManagementSystem.core.config.utilies.Msg;
import dev.patika.vetManagementSystem.core.config.utilies.ResultHelper;
import dev.patika.vetManagementSystem.core.exception.NotFoundException;
import dev.patika.vetManagementSystem.core.result.ResultData;
import dev.patika.vetManagementSystem.dao.AvailableDateRepo;
import dev.patika.vetManagementSystem.dto.request.availableDate.SaveAvailableDateRequest;
import dev.patika.vetManagementSystem.dto.request.availableDate.UpdateAvailableDate;
import dev.patika.vetManagementSystem.dto.response.availableDateResponse.AvailableDateResponse;
import dev.patika.vetManagementSystem.dto.response.availableDateResponse.AvailableDateResponse2;
import dev.patika.vetManagementSystem.entities.Appointment;
import dev.patika.vetManagementSystem.entities.AvailableDate;
import dev.patika.vetManagementSystem.entities.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailableDateManager implements IAvailableDateService {

    @Autowired
    private AvailableDateRepo availableDateRepo;

    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private IModelMapperService modelMapper;

    @Override
    public AvailableDate save(AvailableDate availableDate) {
        return this.availableDateRepo.save(availableDate);
    }

    @Override
    public ResultData<AvailableDateResponse2> save2(SaveAvailableDateRequest saveAvailableDateRequest) {
        try {
            AvailableDate response = this.modelMapper.forRequest().map(saveAvailableDateRequest, AvailableDate.class);

            Doctor doctor = this.doctorService.get(saveAvailableDateRequest.getDoctor().getId());
            response.setDoctor(doctor);
            this.availableDateRepo.save(response);
            return ResultHelper.created(this.modelMapper.forResponse().map(response, AvailableDateResponse2.class));


        }catch (Exception e) {
            return ResultData.error(Msg.VALIDATE_ERROR, "500");
        }
    }

    @Override
    public ResultData<AvailableDateResponse2> get2(long id) {
        try {
            AvailableDate availableDate = this.availableDateRepo.findById(id).orElseThrow();
            AvailableDateResponse2 availableDateResponse = this.modelMapper.forResponse().map(availableDate, AvailableDateResponse2.class);
            return ResultHelper.success(availableDateResponse);

        }catch (Exception e) {
            return ResultData.error(Msg.NOT_FOUND, "500");

        }
    }

    @Override
    public ResultData<AvailableDateResponse2> update2(UpdateAvailableDate updateAvailableDate) {
        Long availableDateId = updateAvailableDate.getId();
        try {
            AvailableDate updatedAvailableDate = this.availableDateRepo.findById(availableDateId).orElseThrow();
            this.modelMapper.forRequest().map(updateAvailableDate, updatedAvailableDate);
            Doctor doctor = this.doctorService.get(updateAvailableDate.getDoctor().getId());
            updatedAvailableDate.setDoctor(doctor);
            this.availableDateRepo.save(updatedAvailableDate);
            AvailableDateResponse2 availableDateResponse = this.modelMapper.forResponse().map(updatedAvailableDate, AvailableDateResponse2.class);
            return ResultHelper.success(availableDateResponse);
        } catch (Exception e) {
            return ResultData.error(Msg.NOT_FOUND, "404");
        }
    }


    @Override
    public AvailableDate get(long id) {
        return this.availableDateRepo.findById(id).orElseThrow(() ->  new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public AvailableDate update(AvailableDate availableDate) {

        return this.availableDateRepo.save(availableDate);
    }

    @Override
    public boolean delete(long id) {
        try {
            AvailableDate availableDate = this.get(id);
            this.availableDateRepo.delete(availableDate);
            return true;
        } catch (NotFoundException ex) {
            return false;
        }

    }


}
