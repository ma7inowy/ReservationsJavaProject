package jwachala.project.reservationsapp;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CarrierModel {

    private String id = UUID.randomUUID().toString();
    private String startCity;
    private String destinationCity;
    private LocalDate date;
    private String companyName;
    private int availability;
    private static int counterID = 0;
    // jeszcze nie wiem czy przechowywac taka liste czy nie (potrzebne do zabawy z rezerwacjami)
    private List<CarrierOrderModel> passengers = new ArrayList<>();

    public CarrierModel() {
    }

    public CarrierModel(String startCity, String destinationCity, LocalDate date, String companyName) {
        this.startCity = startCity;
        this.destinationCity = destinationCity;
        this.companyName = companyName;
        this.date = date;
        this.availability = 10 + counterID;
        counterID++;
    }
}
