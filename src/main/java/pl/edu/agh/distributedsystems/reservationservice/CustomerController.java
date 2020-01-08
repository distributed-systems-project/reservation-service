package pl.edu.agh.distributedsystems.reservationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.distributedsystems.reservationservice.dao.Customer;
import pl.edu.agh.distributedsystems.reservationservice.dao.CustomerRepository;
import pl.edu.agh.distributedsystems.reservationservice.dao.Reservation;
import pl.edu.agh.distributedsystems.reservationservice.dao.ReservationRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        return ResponseEntity.ok(customerRepository.save(customer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable(value = "id") Long customerID) {
        Optional<Customer> customer = customerRepository.findById(customerID);

        if(customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerRepository.findAll());
    }
}
