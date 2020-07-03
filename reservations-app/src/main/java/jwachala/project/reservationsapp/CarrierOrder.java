package jwachala.project.reservationsapp;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CarrierOrder {

    private String id;
    private String name;
    private String surname;
    private Date date;
    private String carrierID;
    private String destinationCity;

    public CarrierOrder(String name, String surname, Date date, String carrierID, String destinationCity) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.carrierID = carrierID;
        this.destinationCity = destinationCity;
    }
}
