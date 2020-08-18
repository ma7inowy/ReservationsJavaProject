package jwachala.project.reservationsapp;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


// SERWIS DLA REPOZYTORIUM STARYCH PRZEWOZOW (ZREALIZOWANYCH)
@NoArgsConstructor
@Service
public class CarrierHistoryServiceImpl implements CarrierHistoryService {

    private CarrierService carrierService;
    private CarrierOrderService carrierOrderService;

    @Autowired
    private CarrierHistoryRepository carrierHistoryRepository;

    public CarrierHistoryServiceImpl(CarrierHistoryRepository carrierHistoryRepository, CarrierService carrierService, CarrierOrderService carrierOrderService) {
        this.carrierHistoryRepository = carrierHistoryRepository;
        this.carrierService = carrierService;
        this.carrierOrderService = carrierOrderService;
    }

    @Autowired
    public CarrierHistoryServiceImpl(CarrierService carrierService, CarrierOrderService carrierOrderService) {
        this.carrierService = carrierService;
        this.carrierOrderService = carrierOrderService;
    }

    @Override
    public List<CarrierModel> getHistoryCarriersbyCompanyName(String companyName) {
        return carrierHistoryRepository.findByCompanyName(companyName);
    }

    @Override
    public List<CarrierModel> refreshHistory() {
        Iterable<CarrierModel> cMList = carrierService.getAllCarriers();
        List<CarrierModel> carriersforRemoveList = new ArrayList<>();

        // przenoszenie obiektow ofert przewozow z CarrierRepository -> CarrierHistory
        for (CarrierModel cM : cMList) {
            if (cM.getDate().compareTo(LocalDate.now()) < 0 || cM.isRealized()) {
                carrierHistoryRepository.save(cM);
                carriersforRemoveList.add(cM);
            }
        }

        // usuwanie starych zleceń/biletow
        List<CarrierOrderModel> CarrierOrderforRemoveList = new ArrayList<>();
        for (CarrierOrderModel coM : carrierOrderService.getCarrierOrderListIterable()) {
            for (CarrierModel cM : carriersforRemoveList) {
                if (coM.getCarrierId().equals(cM.getId()))
                    CarrierOrderforRemoveList.add(coM);
            }
        }

        //usuwanie starych przewozów
        for (CarrierModel i : carriersforRemoveList)
            carrierService.deleteCarrier(i);

        // usuwanie starych biletow / zlecen z bazy
        for (CarrierOrderModel i : CarrierOrderforRemoveList) {
            carrierOrderService.removeCarrierOrder(i);
        }

        return carriersforRemoveList;
    }
}
