package dev.patika.vetManagementSystem.bussiness.abstracts;


import dev.patika.vetManagementSystem.core.result.ResultData;
import dev.patika.vetManagementSystem.dto.request.availableDate.SaveAvailableDateRequest;
import dev.patika.vetManagementSystem.dto.request.availableDate.UpdateAvailableDate;
import dev.patika.vetManagementSystem.dto.response.availableDateResponse.AvailableDateResponse;
import dev.patika.vetManagementSystem.dto.response.availableDateResponse.AvailableDateResponse2;
import dev.patika.vetManagementSystem.entities.AvailableDate;

public interface IAvailableDateService {

    AvailableDate save(AvailableDate availableDate);
    AvailableDate get(long id);
    AvailableDate update(AvailableDate availableDate);
    boolean delete(long id);

    ResultData<AvailableDateResponse2> save2(SaveAvailableDateRequest saveAvailableDateRequest);

    ResultData<AvailableDateResponse2> get2(long id);

    ResultData<AvailableDateResponse2> update2(UpdateAvailableDate updateAvailableDate);
}
