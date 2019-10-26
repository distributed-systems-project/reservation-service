package reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reservation.dao.Reservation;
import reservation.dao.ReservationRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MainController {

    @Autowired
    ReservationRepository reservationRepository;

    @RequestMapping("/")
    public String index() {
        return "Hello!";
    }

    @PostMapping("/reservations")
    public Reservation createReservation(@Valid @RequestBody Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @GetMapping("/reservations/{id}")
    public Reservation getReservation(@PathVariable(value = "id") Long reservationID) {
        return reservationRepository.findById(reservationID)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }
}
