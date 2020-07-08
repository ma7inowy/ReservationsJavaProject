package jwachala.project.reservationsapp;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CarrierOrderModel {

    private String id = UUID.randomUUID().toString();
    private String name;
    private String surname; // zmien na maila
    private LocalDate orderDate;
    private String carrierId;
    private static int counterID = 0;

    public CarrierOrderModel() {
    }

    public CarrierOrderModel(String name, String surname, LocalDate date, String carrierID) {
        this.name = name;
        this.surname = surname;
        this.orderDate = date;
        this.carrierId = carrierID;
        counterID++;

    }
}
