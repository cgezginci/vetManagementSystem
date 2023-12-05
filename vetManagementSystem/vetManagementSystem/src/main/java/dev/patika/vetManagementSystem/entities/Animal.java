package dev.patika.vetManagementSystem.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "animals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id", columnDefinition = "serial")
    private long id; // animal_id

    @Column(name = "animal_name")
    private String name; // animal_name

    @Column(name = "animal_species")
    private String species; // animal_species

    @Column(name = "animal_breed")
    private String breed; // animal_breed

    @Column(name = "animal_gender")
    private String gender; // animal_gender

    @Column(name = "animal_color")
    private String color; // animal_color

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth; // date_of_birth

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "animal" , cascade = CascadeType.REMOVE)
    private List<Vaccine> vaccineList;

    @OneToMany(mappedBy = "animal" , cascade = CascadeType.REMOVE)
    private List<Appointment> appointmentList;

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", breed='" + breed + '\'' +
                ", gender='" + gender + '\'' +
                ", color='" + color + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", customer=" + customer +
                '}';
    }
}
