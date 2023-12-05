package dev.patika.vetManagementSystem.bussiness.concrets;

import dev.patika.vetManagementSystem.bussiness.abstracts.IAnimalService;
import dev.patika.vetManagementSystem.bussiness.abstracts.ICustomerService;
import dev.patika.vetManagementSystem.core.config.modelmapper.IModelMapperService;
import dev.patika.vetManagementSystem.core.config.utilies.Msg;
import dev.patika.vetManagementSystem.core.config.utilies.ResultHelper;
import dev.patika.vetManagementSystem.core.exception.NotFoundException;
import dev.patika.vetManagementSystem.core.result.ListResult;
import dev.patika.vetManagementSystem.core.result.ResultData;
import dev.patika.vetManagementSystem.dao.AnimalRepo;
import dev.patika.vetManagementSystem.dto.request.animal.SaveAnimalRequest;
import dev.patika.vetManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.vetManagementSystem.dto.response.animal.AnimalResponse2;
import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Customer;
import dev.patika.vetManagementSystem.entities.Vaccine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalManager implements IAnimalService {

    @Autowired
    private AnimalRepo animalRepo;

    @Autowired
    private IModelMapperService modelMapper;





    @Override
    public Animal save(Animal animal) {
        return this.animalRepo.save(animal);
    }



    @Override
    public Animal get(long id) {
        return this.animalRepo.findById( id).orElseThrow(() ->  new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public Animal update(Animal animal) {
        this.get(animal.getId());
        return this.animalRepo.save(animal);
    }

    @Override
    public boolean delete(long id) {
        try {
            Animal animal = this.get(id);
            this.animalRepo.delete(animal);
            return true;
        } catch (NotFoundException ex) {
            return false;
        }
    }
    @Override
    public List<Animal> findByName(String name) {
        List<Animal> animals = this.animalRepo.findByNameIgnoreCase(name);

        if (animals.isEmpty()) {
            throw new NotFoundException(Msg.NOT_FOUND);
        }

        return animals;
    }

    @Override
    public Animal getAnimalByName(String name) {
        return animalRepo.findByName(name);
    }

    @Override
    public List<Animal> getAnimalByNames(String name) {
        return this.animalRepo.findByNameIgnoreCase(name);
    }

    @Override
    public Animal findByNames(String name) {
        return null;
    }

    @Override
    public ListResult<AnimalResponse2> findByName2(String name) {
        List<Animal> animals = this.animalRepo.findByNameIgnoreCase(name);

            List<AnimalResponse2> animalResponses = animals.stream()
                    .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse2.class))
                    .collect(Collectors.toList());

            if (animalResponses.isEmpty()) {
                return ResultHelper.notFoundErrorList(Msg.NOT_FOUND);
            }

            return ResultHelper.successList(animalResponses);



    }




}
