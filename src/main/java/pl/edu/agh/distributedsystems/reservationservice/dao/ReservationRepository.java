package pl.edu.agh.distributedsystems.reservationservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    int countAllByStartDateBetweenAndEndDateBetweenAndAndHotelIdAndAndRoomNumber(
            Date startDateStart,
            Date startDateEnd,
            Date endDateStart,
            Date endDateEnd,
            Integer hotelId,
            Integer roomNumber);
}
