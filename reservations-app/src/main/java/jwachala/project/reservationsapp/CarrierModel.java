package jwachala.project.reservationsapp;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//OBIEKT OFERTY PRZEWOZU DANEGO PRZEWOZNIKA NP PIOTRKOW->LODZ 23.07.2020, FIRMA MURAPOL
@Data
@Entity
@NoArgsConstructor
public class CarrierModel {

    @Id
    private String id = UUID.randomUUID().toString();
    private String startCity;
    private String destinationCity;
    private LocalDate date;
    private String companyName;
    private int availability;
    private static int counterID = 0;
    private boolean realized = false; // FLAGA INFORMUJACA CZY PRZEWOZ ZOSTAL JUZ ZREALIZOWANY
    private double price;

    public CarrierModel(String startCity, String destinationCity, LocalDate date, String companyName, double price) {
        this.startCity = startCity;
        this.destinationCity = destinationCity;
        this.companyName = companyName;
        this.date = date;
        this.availability = 10 + counterID;
        counterID++;
        this.price = price;
    }
}
