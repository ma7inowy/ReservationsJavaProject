package jwachala.project.reservationsapp;

import lombok.Data;
import lombok.NoArgsConstructor;

        import java.util.Random;
        import java.util.UUID;

@Data
@NoArgsConstructor
public class CarrierModel {

    private String id;
    private String city;
    private String companyName;
    private static int counterID = 0;

    public CarrierModel(String city, String companyName) {
        this.city = city;
        this.companyName = companyName;
//      this.id = UUID.randomUUID().toString();
        this.id = Integer.toString(counterID);
        counterID++;
    }
}
