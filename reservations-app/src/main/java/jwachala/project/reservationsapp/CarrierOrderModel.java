package jwachala.project.reservationsapp;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;


//OBIEKT BILETU/ZLECENIA NA DANY PRZEJAZD JAKO PODROZNIK
@Data
@NoArgsConstructor
public class CarrierOrderModel {

    private String id = UUID.randomUUID().toString();
    private String email;
    private LocalDate orderDate;
    private String carrierId;
    private boolean paid = false;


    public CarrierOrderModel(String email, LocalDate date, String carrierID) {
        this.email = email;
        this.orderDate = date;
        this.carrierId = carrierID;
    }
}
