package dev.patika.vetManagementSystem.bussiness.concrets;

import dev.patika.vetManagementSystem.bussiness.abstracts.ICustomerService;
import dev.patika.vetManagementSystem.bussiness.abstracts.IVaccineService;
import dev.patika.vetManagementSystem.core.config.utilies.Msg;
import dev.patika.vetManagementSystem.core.exception.NotFoundException;
import dev.patika.vetManagementSystem.dao.VaccineRepo;
import dev.patika.vetManagementSystem.dto.request.vaccine.SaveVaccineRequest;
import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Customer;
import dev.patika.vetManagementSystem.entities.Vaccine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VaccineManager implements IVaccineService {

    @Autowired
    private VaccineRepo vaccineRepo;



    @Override
    public Vaccine save(Vaccine vaccine) {
        return this.vaccineRepo.save(vaccine);
    }

    @Override
    public Vaccine get(long id) {
        return this.vaccineRepo.findById(id).orElseThrow();
    }

    @Override
    public Vaccine update(Vaccine vaccine) {
        return this.vaccineRepo.save(vaccine);
    }

    @Override
    public boolean delete(long id) {
        try {
            Vaccine vaccine = this.get(id);
            this.vaccineRepo.delete(vaccine);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public Vaccine getVaccineByName(String name) {
        return vaccineRepo.findByNameIgnoreCase(name)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }



    @Override
    public List<Vaccine> getVaccinesByName(String vaccineName) {
        return vaccineRepo.findByName(vaccineName);
    }



    @Override
    public List<Vaccine> getVaccinesByDate(LocalDate protectionStartDate, LocalDate protectionEndDate) {
        // Belirtilen tarih aralığındaki aşıları getir
        return vaccineRepo.findByProtectionStartDateBeforeAndProtectionEndDateAfter(protectionEndDate, protectionStartDate);
    }

    @Override
    public List<Vaccine> findByName(String name) {
        List<Vaccine> vaccines = vaccineRepo.findByNameIgnoreCase(name);
        if (vaccines.isEmpty()) {
            throw new NotFoundException(Msg.NOT_FOUND);
        }

        return vaccines;
    }

}





