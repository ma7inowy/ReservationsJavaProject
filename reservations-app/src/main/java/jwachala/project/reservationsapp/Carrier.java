package jwachala.project.reservationsapp;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;
import java.util.UUID;

@Data
public class Carrier {

    private String id;
    private String city;
    private String companyName;

    public Carrier(String city, String companyName) {
        this.city = city;
        this.companyName = companyName;
        this.id = UUID.randomUUID().toString();
    }
}
