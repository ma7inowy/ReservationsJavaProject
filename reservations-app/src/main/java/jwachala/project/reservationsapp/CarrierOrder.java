package jwachala.project.reservationsapp;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
public class CarrierOrder {

    private String id;
    private String name;
    private String surname;
    private LocalDate date;
    private String carrierID;
    private String destinationCity;
    private static int counterID  = 0;

    public CarrierOrder(String name, String surname, LocalDate date, String carrierID, String destinationCity) {
//        this.id = UUID.randomUUID().toString();
        this.id = Integer.toString(counterID);
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.carrierID = carrierID;
        this.destinationCity = destinationCity;
        counterID++;

    }
}
