package jwachala.project.reservationsapp;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CarrierOrderTravelerDTO {
    private String email;
    private LocalDate orderDate;
    private String carrierId;
}
