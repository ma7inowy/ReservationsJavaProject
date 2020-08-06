package jwachala.project.reservationsapp;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CarrierOrderDTO {

    private String email;
    private LocalDate orderDate;
    private String carrierId;
    private boolean paid;

}
