package pl.edu.agh.distributedsystems.reservationservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.agh.distributedsystems.reservationservice.dao.Customer;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ReservationServiceApp.class)
public class CustomerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getURL() {
        return "http://localhost:" + port + "/";
    }

    @Test
    public void testAddCustomer() {
        Customer customer =
                new Customer("Krzysiu", "Kowalski", "321654987", "Zadupie dolne", "123321", "login", "pass");
        HttpEntity<Customer> httpEntity = new HttpEntity<>(customer);
        ResponseEntity<Customer> responseEntity =
                restTemplate.exchange(getURL() + "/customers", HttpMethod.POST, httpEntity, Customer.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Customer result = responseEntity.getBody();

        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getFirstName()).isEqualTo("Krzysiu");
    }

    @Test
    @DependsOn("testAddCustomer")
    public void testGetCustomer() {
        Customer newCustomer =
                new Customer("David", "Bowie", "543345543", "Gdzies", "64456654", "login", "pass");
        Customer response =
                restTemplate.exchange(getURL() + "/customers", HttpMethod.POST, new HttpEntity<>(newCustomer), Customer.class)
                    .getBody();

        Customer gotCustomer = restTemplate.getForObject(getURL() + "/customers/" + response.getId(), Customer.class);

        assertThat(gotCustomer.getFirstName()).isEqualTo("David");
    }

    @Test
    public void getAllCustomers() {
        Customer customer1 =
                new Customer("Krzysiu", "Kowalski", "321654987", "Zadupie dolne", "123321", "login", "pass");
        Customer customer2 =
                new Customer("David", "Bowie", "543345543", "Gdzies", "64456654", "login", "pass");
        restTemplate.postForObject(getURL() + "/customers", customer1, Customer.class);
        restTemplate.postForObject(getURL() + "/customers", customer2, Customer.class);

        Customer[] result = restTemplate.getForObject(getURL() + "/customers", Customer[].class);
        assertThat(result.length).isEqualTo(2);
    }
}