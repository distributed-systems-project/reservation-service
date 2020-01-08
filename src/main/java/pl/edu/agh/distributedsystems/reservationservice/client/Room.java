package pl.edu.agh.distributedsystems.reservationservice.client;

import lombok.Data;

@Data
public class Room {

    private Long id;
    private int roomNumber;
    private int floorNumber;
    private double area;
    private RoomType roomType;

    protected Room() {
    }

    public enum RoomType {
        SINGLE,
        DOUBLE,
        TRIPLE,
        QUAD,
        TWIN,
        STUDIO
    }
}
