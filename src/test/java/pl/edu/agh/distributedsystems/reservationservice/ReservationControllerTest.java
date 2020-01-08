package pl.edu.agh.distributedsystems.reservationservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.agh.distributedsystems.reservationservice.ReservationController.ReservationLite;
import pl.edu.agh.distributedsystems.reservationservice.client.Building;
import pl.edu.agh.distributedsystems.reservationservice.client.BuildingClient;
import pl.edu.agh.distributedsystems.reservationservice.dao.Customer;
import pl.edu.agh.distributedsystems.reservationservice.dao.Reservation;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ReservationServiceApp.class)
public class ReservationControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private BuildingClient buildingClient;

    private String getURL() {
        return "http://localhost:" + port + "/";
    }

    @Test
    public void testAddSingleReservation() {
        Customer customer =
                new Customer("Krzysiu", "Kowalski", "321654987", "Zadupie dolne", "123321");
        customer = restTemplate.postForObject(getURL() + "/customers", customer, Customer.class);
        when(buildingClient.findByHotel(1L)).thenReturn(new Building("ABC", 10));
        ReservationLite reservation = new ReservationLite(
                customer.getId(),
                new Date(2019, 10, 10),
                new Date(2019, 11, 10),
                new Date(2019, 11, 17),
                1, 1);
        ResponseEntity<Reservation> result =
                restTemplate.exchange(getURL() + "/reservations", HttpMethod.POST, new HttpEntity<>(reservation), Reservation.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getCustomer().getFirstName()).isEqualTo("Krzysiu");
    }

    @Test
    public void getSingleReservation() {
        testAddSingleReservation();
        ResponseEntity<Reservation> result =
            restTemplate.exchange(getURL() + "/reservations/2", HttpMethod.GET, null, Reservation.class);
        assertThat(result.getBody().getCustomer().getFirstName()).isEqualTo("Krzysiu");
    }
}
