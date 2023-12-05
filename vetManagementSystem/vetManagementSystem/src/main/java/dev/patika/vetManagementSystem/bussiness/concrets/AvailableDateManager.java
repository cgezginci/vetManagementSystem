package dev.patika.vetManagementSystem.bussiness.concrets;

import dev.patika.vetManagementSystem.bussiness.abstracts.IAvailableDateService;
import dev.patika.vetManagementSystem.core.config.utilies.Msg;
import dev.patika.vetManagementSystem.core.exception.NotFoundException;
import dev.patika.vetManagementSystem.dao.AvailableDateRepo;
import dev.patika.vetManagementSystem.entities.AvailableDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvailableDateManager implements IAvailableDateService {

    @Autowired
    private AvailableDateRepo availableDateRepo;

    @Override
    public AvailableDate save(AvailableDate availableDate) {
        return this.availableDateRepo.save(availableDate);
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
