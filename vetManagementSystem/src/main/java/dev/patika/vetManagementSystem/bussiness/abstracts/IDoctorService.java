package dev.patika.vetManagementSystem.bussiness.abstracts;

import dev.patika.vetManagementSystem.core.result.ListResult;
import dev.patika.vetManagementSystem.core.result.ResultData;
import dev.patika.vetManagementSystem.dto.request.doctor.SaveDoctorRequest;
import dev.patika.vetManagementSystem.dto.request.doctor.UpdateDoctorRequest;
import dev.patika.vetManagementSystem.dto.response.doctor.DoctorResponse;
import dev.patika.vetManagementSystem.dto.response.doctor.DoctorResponse2;
import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Customer;
import dev.patika.vetManagementSystem.entities.Doctor;

import java.util.List;

public interface IDoctorService {

        Doctor save(Doctor doctor);
        Doctor get(long id);
        Doctor update(Doctor doctor);
        boolean delete(long id);

        List<Doctor> findByName(String name);

        ResultData<DoctorResponse2> save2(SaveDoctorRequest saveDoctorRequest);

        ListResult<DoctorResponse> findByName2(String name);

        ResultData<DoctorResponse2> update2(UpdateDoctorRequest updateDoctorRequest);

        List<Doctor> getDoctorByNames(String name);

}
