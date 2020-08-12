package jwachala.project.reservationsapp;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class Carrier {
    @Id
    private String id=  UUID.randomUUID().toString();
    private String startCity;
    private String destinationCity;
    private LocalDate date;
    private String companyName;
    private int availability;
    private static int counterID = 0;
    private boolean realized;
    private double price;

    public Carrier(String startCity, String destinationCity, LocalDate date, String companyName, double price) {
        this.startCity = startCity;
        this.destinationCity = destinationCity;
        this.companyName = companyName;
        this.date = date;
        this.availability = 10 + counterID;
        counterID++;
        this.price = price;
    }
}
