package jwachala.project.reservationsapp;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CarrierOrderModel {

    private String id = UUID.randomUUID().toString();
    private String email;
    private LocalDate orderDate;
    private String carrierId;
    private String status = "not paid";



    public CarrierOrderModel(String email, LocalDate date, String carrierID) {
        this.email = email;
        this.orderDate = date;
        this.carrierId = carrierID;
    }
}
