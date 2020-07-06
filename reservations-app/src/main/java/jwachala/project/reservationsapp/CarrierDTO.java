package jwachala.project.reservationsapp;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class CarrierDTO {

    private String startCity;
    private String destinationCity;
    private LocalDate date;
    private String companyName;
    private int availability;
//    private Details details;

//    @Data
//    static class Details{
//        private int distance;
//    }

}
