package dev.patika.vetManagementSystem.bussiness.abstracts;

import dev.patika.vetManagementSystem.entities.Customer;
import dev.patika.vetManagementSystem.entities.Doctor;

import java.util.List;

public interface IDoctorService {

        Doctor save(Doctor doctor);
        Doctor get(long id);
        Doctor update(Doctor doctor);
        boolean delete(long id);

        List<Doctor> findByName(String name);

}
