package jwachala.project.reservationsapp;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class CarrierRepostiory {

    private List<CarrierModel> carrierList;

    public CarrierRepostiory() {
        this.carrierList = new ArrayList<>();
        carrierList.add(new CarrierModel("City1", "destCity1", LocalDate.now(), "Company0"));
        carrierList.add(new CarrierModel("City2", "destCity2",LocalDate.now().plusDays(5), "Company1"));
        carrierList.add(new CarrierModel("City2", "destCity4",LocalDate.now().plusDays(3), "Company2"));
        carrierList.add(new CarrierModel("City3", "destCity4",LocalDate.now().plusDays(1), "Company3"));
        carrierList.add(new CarrierModel("City5", "destCity5",LocalDate.now().plusDays(1), "Company3"));

    }

    public List<CarrierModel> getCarriersbyStartCity(String city) {
        List<CarrierModel> carrierListbyCity = new ArrayList<>();
        for (var carrier : carrierList) {
            if (carrier.getStartCity().toLowerCase().equals(city.toLowerCase())) {
                carrierListbyCity.add(carrier);
            }
        }
        return carrierListbyCity;
    }

    public List<CarrierModel> getCarriersbyCompanyName(String companyName) {
        List<CarrierModel> carrierListbyCompanyName = new ArrayList<>();
        for (var carrier : carrierList) {
            if (carrier.getCompanyName().toLowerCase().equals(companyName.toLowerCase())) {
                carrierListbyCompanyName.add(carrier);
            }
        }
        return carrierListbyCompanyName;
    }
}
