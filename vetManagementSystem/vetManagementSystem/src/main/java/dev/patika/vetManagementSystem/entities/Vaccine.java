package dev.patika.vetManagementSystem.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vaccines")

public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccine_id", columnDefinition = "serial")
    private long id; // vaccine_id

    @Column(name = "vaccine_name")
    private String name; // vaccine_name

    @Column(name = "protection_start_date")
    private LocalDate protectionStartDate; // protection_start_date

    @Column(name = "protection_end_date")
    private LocalDate protectionEndDate; // protection_end_date

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_vaccine_id", referencedColumnName = "animal_id")
    private Animal animal;

    @Override
    public String toString() {
        return "Vaccine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", protectionStartDate=" + protectionStartDate +
                ", protectionEndDate=" + protectionEndDate +
                ", animal=" + animal +
                '}';
    }
}
