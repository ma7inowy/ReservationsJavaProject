package jwachala.project.reservationsapp;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CarrierModel {

    private String id;
    private String startCity;
    private String destinationCity;
    private LocalDate date;
    private String companyName;
    private static int counterID = 0;

    public CarrierModel() {
        this.id = Integer.toString(counterID);
        counterID++;
    }

    public CarrierModel(String startCity, String destinationCity, LocalDate date, String companyName) {
        this.startCity = startCity;
        this.destinationCity = destinationCity;
        this.companyName = companyName;
        this.id = Integer.toString(counterID);
        this.date = date;
        counterID++;
    }
}
