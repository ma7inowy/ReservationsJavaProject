package jwachala.project.reservationsapp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CarrierDTO {

    private String city;
    private String companyName;
    private Details details;

    @Data
    static class Details{
        private int distance;
    }

}
