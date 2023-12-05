package dev.patika.vetManagementSystem.dao;

import dev.patika.vetManagementSystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    List<Customer> findByNameIgnoreCase(String name);




}
