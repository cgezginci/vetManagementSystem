package dev.patika.vetManagementSystem.dao;

import dev.patika.vetManagementSystem.entities.Animal;
import dev.patika.vetManagementSystem.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine,Long> {

    List<Vaccine> findByName(String vaccineName);

    List<Vaccine> findByNameIgnoreCase(String name);

    List<Vaccine> findByProtectionStartDateAfterAndProtectionEndDateBefore(LocalDate protectionStartDate, LocalDate protectionEndDate);

    List<Vaccine> findByAnimalIdAndNameAndProtectionEndDateAfter(Long animalId, String name, LocalDate currentDate);




}
