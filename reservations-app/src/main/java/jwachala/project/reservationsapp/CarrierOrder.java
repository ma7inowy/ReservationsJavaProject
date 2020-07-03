package jwachala.project.reservationsapp;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CarrierOrder {

    private String id;
    private String name;
    private String surname;
    private LocalDate date;
    private String carrierId;
    private String destinationCity;
    private static int counterID  = 0;

    public CarrierOrder(String name, String surname, LocalDate date, String carrierID, String destinationCity) {
//        this.id = UUID.randomUUID().toString();
        this.id = Integer.toString(counterID);
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.carrierId = carrierID;
        this.destinationCity = destinationCity;
        counterID++;

    }
}
