package reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservation.dao.Customer;
import reservation.dao.CustomerRepository;
import reservation.dao.Reservation;
import reservation.dao.ReservationRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class MainController {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping("/")
    public String index() {
        return "Hello!";
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody Reservation reservation) {
        return ResponseEntity.ok(reservationRepository.save(reservation));
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable(value = "id") Long reservationID) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationID);

        if(reservation.isPresent()) {
            return ResponseEntity.ok(reservation.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservationRepository.findAll());
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        return ResponseEntity.ok(customerRepository.save(customer));
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable(value = "id") Long customerID) {
        Optional<Customer> customer = customerRepository.findById(customerID);

        if(customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerRepository.findAll());
    }
}
