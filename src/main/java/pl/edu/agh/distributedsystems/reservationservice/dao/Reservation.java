package pl.edu.agh.distributedsystems.reservationservice.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnegative;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @NonNull
    private Customer customer;

    @NonNull @Temporal(TemporalType.DATE) private Date placedAtDate;
    @NonNull @Temporal(TemporalType.DATE) private Date startDate;
    @NonNull @Temporal(TemporalType.DATE) private Date endDate;
    @NonNull private Integer hotelId;
    @NonNull private Integer roomNumber;
}
