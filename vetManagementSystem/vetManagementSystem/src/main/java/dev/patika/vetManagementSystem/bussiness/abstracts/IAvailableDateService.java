package dev.patika.vetManagementSystem.bussiness.abstracts;


import dev.patika.vetManagementSystem.entities.AvailableDate;

public interface IAvailableDateService {

    AvailableDate save(AvailableDate availableDate);
    AvailableDate get(long id);
    AvailableDate update(AvailableDate availableDate);
    boolean delete(long id);
}
