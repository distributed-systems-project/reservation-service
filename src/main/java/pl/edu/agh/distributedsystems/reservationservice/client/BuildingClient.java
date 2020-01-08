package pl.edu.agh.distributedsystems.reservationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "building-service")
public interface BuildingClient {

    @GetMapping("/buildings/{hotelId}")
    Building findByHotel(@PathVariable("hotelId") Long hotelId);
}
