package dev.patika.vetManagementSystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "doctor_id", columnDefinition = "serial")
        private long id; // doctor_id

        @Column(name = "doctor_name")
        private String name; // doctor_name

        @Column(name = "doctor_phone")
        private String phone; // doctor_phone

        @Column(name = "doctor_mail")
        private String mail; // doctor_mail

        @Column(name = "doctor_address")
        private String address; // doctor_address

        @Column(name = "doctor_city")
        private String city; // doctor_city

        @OneToMany(mappedBy = "doctor" , cascade = CascadeType.REMOVE)
        private List<AvailableDate> availableDateList;

        @OneToMany(mappedBy = "doctor" , cascade = CascadeType.REMOVE)
        private List<Appointment> appointmentList;

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
