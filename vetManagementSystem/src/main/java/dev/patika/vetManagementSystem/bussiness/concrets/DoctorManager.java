package dev.patika.vetManagementSystem.bussiness.concrets;

import dev.patika.vetManagementSystem.bussiness.abstracts.IDoctorService;
import dev.patika.vetManagementSystem.core.config.modelmapper.IModelMapperService;
import dev.patika.vetManagementSystem.core.config.utilies.Msg;
import dev.patika.vetManagementSystem.core.config.utilies.ResultHelper;
import dev.patika.vetManagementSystem.core.exception.NotFoundException;
import dev.patika.vetManagementSystem.core.result.ListResult;
import dev.patika.vetManagementSystem.core.result.ResultData;
import dev.patika.vetManagementSystem.dao.DoctorRepo;
import dev.patika.vetManagementSystem.dto.request.doctor.SaveDoctorRequest;
import dev.patika.vetManagementSystem.dto.request.doctor.UpdateDoctorRequest;
import dev.patika.vetManagementSystem.dto.response.doctor.DoctorResponse;
import dev.patika.vetManagementSystem.dto.response.doctor.DoctorResponse2;
import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Customer;
import dev.patika.vetManagementSystem.entities.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorManager implements IDoctorService {

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private IModelMapperService modelMapper;

    @Override
    public Doctor save(Doctor doctor) {
        return this.doctorRepo.save(doctor);
    }


    @Override
    public ResultData<DoctorResponse2> save2(SaveDoctorRequest saveDoctorRequest) {
        try {
            Doctor response = this.modelMapper.forRequest().map(saveDoctorRequest, Doctor.class);
            this.doctorRepo.save(response);
            return ResultHelper.created(this.modelMapper.forResponse().map(response, DoctorResponse2.class));

        }catch (Exception e) {
            return ResultData.error(Msg.VALIDATE_ERROR, "500");
        }
    }

    @Override
    public ListResult<DoctorResponse> findByName2(String name) {

            List<Doctor> doctors = this.doctorRepo.findByNameIgnoreCase(name);

            List<DoctorResponse> doctorResponses = doctors.stream()
                    .map(doctor -> this.modelMapper.forResponse().map(doctor, DoctorResponse.class))
                    .collect(Collectors.toList());

            if (doctorResponses.isEmpty()) {
                return ResultHelper.notFoundErrorList(Msg.NOT_FOUND);
            }

            return ResultHelper.successList(doctorResponses);


    }

    @Override
    public ResultData<DoctorResponse2> update2(UpdateDoctorRequest updateDoctorRequest) {
        long doctorId = updateDoctorRequest.getId();

        try {
            Doctor updatedDoctor = this.doctorRepo.findById(doctorId).orElseThrow();

            this.modelMapper.forRequest().map(updateDoctorRequest, updatedDoctor);

            this.doctorRepo.save(updatedDoctor);
            DoctorResponse2 doctorResponse = this.modelMapper.forResponse().map(updatedDoctor, DoctorResponse2.class);
            return ResultHelper.success(doctorResponse);
        }catch (Exception e) {
            return ResultData.error(Msg.NOT_FOUND, "500");
        }
    }


    @Override
    public Doctor get(long id) {
        return this.doctorRepo.findById(id).orElseThrow(() ->  new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public Doctor update(Doctor doctor) {
        System.out.println(this.get(doctor.getId()));
        this.get(doctor.getId());

        return this.doctorRepo.save(doctor);
    }

    @Override
    public boolean delete(long id) {
        try {
            Doctor doctor = this.get(id);
            this.doctorRepo.delete(doctor);
            return true;
        } catch (NotFoundException ex) {
            return false;
        }

    }

    @Override
    public List<Doctor> findByName(String name) {
        List<Doctor> doctors = doctorRepo.findByNameIgnoreCase(name);
        if (doctors.isEmpty()) {
            throw new NotFoundException(Msg.NOT_FOUND);
        }

        return doctors;
    }

    @Override
    public List<Doctor> getDoctorByNames(String name) {
        return this.doctorRepo.findByNameIgnoreCase(name);
    }


}
