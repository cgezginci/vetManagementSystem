package dev.patika.vetManagementSystem.bussiness.concrets;

import dev.patika.vetManagementSystem.bussiness.abstracts.IDoctorService;
import dev.patika.vetManagementSystem.core.config.utilies.Msg;
import dev.patika.vetManagementSystem.core.exception.NotFoundException;
import dev.patika.vetManagementSystem.dao.DoctorRepo;
import dev.patika.vetManagementSystem.entities.Customer;
import dev.patika.vetManagementSystem.entities.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorManager implements IDoctorService {

    @Autowired
    private DoctorRepo doctorRepo;

    @Override
    public Doctor save(Doctor doctor) {
        return this.doctorRepo.save(doctor);
    }

    @Override
    public Doctor get(long id) {
        return this.doctorRepo.findById(id).orElseThrow(() ->  new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public Doctor update(Doctor doctor) {
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
}
