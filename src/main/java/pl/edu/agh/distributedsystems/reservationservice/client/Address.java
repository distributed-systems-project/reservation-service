package pl.edu.agh.distributedsystems.reservationservice.client;

import lombok.Data;

@Data
public class Address {
    private Long id;
    private Building building;

    private String houseNumber;
    private String street;
    private String zipCode;
    private String city;
    private String country;

    protected Address() {
    }

    public Address(String houseNumber, String street, String zipCode, String city, String country) {
        this.houseNumber = houseNumber;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }
}
