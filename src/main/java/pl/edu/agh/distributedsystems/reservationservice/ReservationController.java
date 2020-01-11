package pl.edu.agh.distributedsystems.reservationservice;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.distributedsystems.reservationservice.client.BuildingClient;
import pl.edu.agh.distributedsystems.reservationservice.dao.CustomerRepository;
import pl.edu.agh.distributedsystems.reservationservice.dao.Reservation;
import pl.edu.agh.distributedsystems.reservationservice.dao.ReservationRepository;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BuildingClient buildingClient;

    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor
    public static class ReservationLite {
        private Long id;
        @NonNull private Long customerId;
        @NonNull private Date placedAtDate;
        @NonNull private Date startDate;
        @NonNull private Date endDate;
        @NonNull private Integer hotelId;
        @NonNull private Integer roomNumber;
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@Valid @RequestBody ReservationLite reservationLite) {
        try {
            buildingClient.findByHotel(reservationLite.getHotelId().longValue());

            if(0 != reservationRepository.countAllByStartDateBetweenAndEndDateBetweenAndAndHotelIdAndAndRoomNumber(
                    reservationLite.startDate,
                    reservationLite.endDate,
                    reservationLite.startDate,
                    reservationLite.endDate,
                    reservationLite.hotelId,
                    reservationLite.roomNumber
            )) {
                ResponseEntity.badRequest().build();
            }

            Reservation reservation = new Reservation(
                    customerRepository.findById(reservationLite.getCustomerId()).get(),
                    reservationLite.placedAtDate,
                    reservationLite.startDate,
                    reservationLite.endDate,
                    reservationLite.hotelId,
                    reservationLite.roomNumber);

            reservationRepository.save(reservation);
            return ResponseEntity.ok(reservation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable(value = "id") Long reservationID) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationID);
        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservationRepository.findAll());
    }
}
