package jwachala.project.reservationsapp;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CarrierRepostiory {

    private List<Carrier> carrierList;

    public CarrierRepostiory() {
        this.carrierList = new ArrayList<>();
        carrierList.add(new Carrier("City1", "Company1"));
        carrierList.add(new Carrier("City1", "Company2"));
        carrierList.add(new Carrier("City1", "Company3"));
        carrierList.add(new Carrier("City2", "Company4"));
        carrierList.add(new Carrier("City3", "Company5"));
    }

    public List<Carrier> getCarriersbyCity(String city) {
        List<Carrier> carrierListbyCity = new ArrayList<>();
        for (Carrier carrier : carrierList) {
            if (carrier.getCity().toLowerCase().equals(city.toLowerCase())) {
                carrierListbyCity.add(carrier);
            }
        }
        return carrierListbyCity;
    }
}
