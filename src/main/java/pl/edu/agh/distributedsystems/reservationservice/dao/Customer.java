package pl.edu.agh.distributedsystems.reservationservice.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull private String firstName;
    @NonNull private String secondName;
    @NonNull private String phoneNumber;
    @NonNull private String address;
    @NonNull private String pesel;
}
