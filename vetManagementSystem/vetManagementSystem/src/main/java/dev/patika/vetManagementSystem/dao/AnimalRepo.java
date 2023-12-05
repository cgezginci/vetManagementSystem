package dev.patika.vetManagementSystem.dao;

import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Long> {

    List<Animal> findByNameIgnoreCase(String name);

    Animal findByName(String name);

}
