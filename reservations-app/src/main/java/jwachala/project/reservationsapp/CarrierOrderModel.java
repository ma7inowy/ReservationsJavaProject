package jwachala.project.reservationsapp;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CarrierOrderModel {

    private String id;
    private String name;
    private String surname;
    private LocalDate orderDate;
    private String carrierId; // lub caly obiekt, carrierId to konkretny "przewóz"
    private static int counterID  = 0;

    public CarrierOrderModel(){
        this.id = Integer.toString(counterID);
        counterID++;
    }

    public CarrierOrderModel(String name, String surname, LocalDate date, String carrierID) {
//      this.id = UUID.randomUUID().toString();
        this.id = Integer.toString(counterID);
        this.name = name;
        this.surname = surname;
        this.orderDate = date;
        this.carrierId = carrierID;
        counterID++;

    }
}
