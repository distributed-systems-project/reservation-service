package pl.edu.agh.distributedsystems.reservationservice.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.distributedsystems.reservationservice.dao.Customer;
import pl.edu.agh.distributedsystems.reservationservice.dao.CustomerRepository;

import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
public class LoginController {

    private CustomerRepository customerRepository;

    @Autowired
    public LoginController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping("/customer/login")
    public ResponseEntity<?> login(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String[] credentials = getCredentials(authHeader);
        Optional<Customer> customerOptional = customerRepository.findByLoginAndPassword(credentials[0], credentials[1]);

        return customerOptional.<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("{\"message\":\"Invalid login or password.\"}"));

    }

    private String[] getCredentials(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String encodedCredentials = authHeader.substring(6);
        String decodedCredentials = new String(Base64Utils.decodeFromString(encodedCredentials), UTF_8);
        return decodedCredentials.split(":");
    }
}
