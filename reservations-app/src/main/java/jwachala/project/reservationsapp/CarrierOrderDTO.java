package jwachala.project.reservationsapp;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CarrierOrderDTO {

    private String name;
    private String surname;
    private LocalDate orderDate;
    private String carrierId;
}
