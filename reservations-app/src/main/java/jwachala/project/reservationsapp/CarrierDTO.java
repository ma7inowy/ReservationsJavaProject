package jwachala.project.reservationsapp;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class CarrierDTO {

    private String id;
    private String startCity;
    private String destinationCity;
    private LocalDate date;
    private String companyName;
    private int availability; // nie pokazywac ile wolnych tylko pokazac
    private boolean realized;
    // POLE CENA ???


//    private Details details;

//    @Data
//    static class Details{
//        private int distance;
//    }

}
