package jwachala.project.reservationsapp;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class CarrierRepostiory {

    private List<CarrierModel> carrierList;

    public CarrierRepostiory() {
        this.carrierList = new ArrayList<>();
        carrierList.add(new CarrierModel("City1", "Company1"));
        carrierList.add(new CarrierModel("City1", "Company2"));
        carrierList.add(new CarrierModel("City1", "Company3"));
        carrierList.add(new CarrierModel("City2", "Company4"));
        carrierList.add(new CarrierModel("City3", "Company5"));
    }

    public List<CarrierModel> getCarriersbyCity(String city) {
        List<CarrierModel> carrierListbyCity = new ArrayList<>();
        for (CarrierModel carrier : carrierList) {
            if (carrier.getCity().toLowerCase().equals(city.toLowerCase())) {
                carrierListbyCity.add(carrier);
            }
        }
        return carrierListbyCity;
    }

    public List<CarrierModel> getCarriersbyCompanyName(String companyName) {
        List<CarrierModel> carrierListbyCompanyName = new ArrayList<>();
        for (CarrierModel carrier : carrierList) {
            if (carrier.getCompanyName().toLowerCase().equals(companyName.toLowerCase())) {
                carrierListbyCompanyName.add(carrier);
            }
        }
        return carrierListbyCompanyName;
    }
}
