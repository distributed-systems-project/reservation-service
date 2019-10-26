package reservation.dao;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

    private String customerName;
    private String customerAddress;
    private String customerPhoneAddress;

    private Date placedAtDate;
    private Date startDate;
    private Date endDate;

    private Integer roomNumber;
}
