package dev.patika.vetManagementSystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "available_dates")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDate {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "available_date_id", columnDefinition = "serial")
        private long id; // available_date_id

        @Column(name = "available_date")
        private LocalDate date; // available_date

        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "available_date_doctor_id", referencedColumnName = "doctor_id")
        private Doctor doctor;

    @Override
    public String toString() {
        return "AvailableDate{" +
                "id=" + id +
                ", date=" + date +
                ", doctor=" + doctor +
                '}';
    }
}
