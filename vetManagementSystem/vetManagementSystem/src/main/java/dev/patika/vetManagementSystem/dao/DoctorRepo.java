package dev.patika.vetManagementSystem.dao;

import dev.patika.vetManagementSystem.entities.Customer;
import dev.patika.vetManagementSystem.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepo   extends JpaRepository<Doctor,Long> {
    List<Doctor> findByNameIgnoreCase(String name);

}
